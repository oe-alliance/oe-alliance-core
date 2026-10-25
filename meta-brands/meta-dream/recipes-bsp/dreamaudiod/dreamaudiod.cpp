/*
 * dreamaudiod - audio decoding service for the DreamOne/DreamTwo
 *
 * The SoC has no audio decoder. The kernel offers one per audio decoder of
 * the DVB API (/dev/dvb/adapter0/dreamaudio0): the player drives it like any
 * audio decoder and its demux filters deliver the transport stream there.
 * This service reads the stream, decodes it or packs it for passthrough and
 * writes it with its PTS to the S/PDIF driver, which paces the output.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 */

#include <algorithm>
#include <cctype>
#include <cerrno>
#include <climits>
#include <ctime>
#include <cmath>
#include <csignal>
#include <cstdarg>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <fcntl.h>
#include <fstream>
#include <map>
#include <poll.h>
#include <string>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <syslog.h>
#include <unistd.h>
#include <vector>

#include <alsa/asoundlib.h>
#include <linux/dvb/audio.h>
#include <neaacdec.h>

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libavutil/audio_fifo.h>
#include <libavutil/opt.h>
#include <libswresample/swresample.h>
}

#include "dreamaudio.h"

#define TS_SIZE 188
#define NOPTS AV_NOPTS_VALUE

static bool g_debug;
static volatile sig_atomic_t g_quit;

static void logMsg(int prio, const char *fmt, ...)
{
	va_list ap;
	va_start(ap, fmt);
	if (g_debug) {
		va_list aq;
		va_copy(aq, ap);
		fputs("dreamaudiod: ", stderr);
		vfprintf(stderr, fmt, aq);
		fputc('\n', stderr);
		va_end(aq);
	}
	vsyslog(prio, fmt, ap);
	va_end(ap);
}

#define LOG(...) logMsg(LOG_INFO, __VA_ARGS__)
#define DBG(...) do { if (g_debug) logMsg(LOG_DEBUG, __VA_ARGS__); } while (0)

static std::string readFile(const char *path)
{
	std::ifstream f(path);
	std::string s;
	std::getline(f, s, '\0');
	return s;
}

static int readInt(const char *path, int def)
{
	std::string s = readFile(path);
	return s.empty() ? def : atoi(s.c_str());
}

static void writeFile(const char *path, const char *value)
{
	int fd = open(path, O_WRONLY | O_CLOEXEC);
	if (fd < 0)
		return;
	if (write(fd, value, strlen(value)) < 0)
		DBG("write %s: %m", path);
	close(fd);
}

/* the enigma2 settings, reread when the file changes */
class Settings
{
	std::map<std::string, std::string> m_values;
	time_t m_mtime = 0;

public:
	void load()
	{
		struct stat st;
		if (stat("/etc/enigma2/settings", &st) < 0 || st.st_mtime == m_mtime)
			return;
		m_mtime = st.st_mtime;
		m_values.clear();
		std::ifstream f("/etc/enigma2/settings");
		std::string line;
		while (std::getline(f, line)) {
			size_t eq = line.find('=');
			if (eq != std::string::npos && line[0] != '#')
				m_values[line.substr(0, eq)] = line.substr(eq + 1);
		}
	}

	std::string get(const char *key, const char *def = "")
	{
		load();
		auto it = m_values.find(key);
		return it == m_values.end() ? def : it->second;
	}

	int getInt(const char *key, int def)
	{
		std::string v = get(key);
		return v.empty() ? def : atoi(v.c_str());
	}
};

static Settings g_settings;

/* the EDID line of a codec the HDMI sink decodes, empty if it does not */
static std::string sinkLine(const char *name)
{
	std::string cap = readFile("/sys/class/amhdmitx/amhdmitx0/aud_cap");
	for (size_t pos = 0; (pos = cap.find(name, pos)) != std::string::npos; pos++)
		if ((pos == 0 || cap[pos - 1] == '\n') && cap[pos + strlen(name)] == ',')
			return cap.substr(pos, cap.find('\n', pos) - pos);
	return std::string();
}

/* AC-4 in a short audio descriptor of the raw EDID (CTA-861-G 7.5.2,
 * audio format code 15, extension type 12) */
static bool sinkAc4()
{
	std::string hex = readFile("/sys/class/amhdmitx/amhdmitx0/rawedid");
	std::vector<uint8_t> edid;
	for (size_t i = 0; i + 1 < hex.size(); i += 2) {
		if (!isxdigit((unsigned char)hex[i]) || !isxdigit((unsigned char)hex[i + 1]))
			break;
		edid.push_back((uint8_t)strtoul(hex.substr(i, 2).c_str(), nullptr, 16));
	}
	for (size_t block = 128; block + 128 <= edid.size(); block += 128) {
		const uint8_t *b = &edid[block];
		if (b[0] != 0x02)
			continue;
		size_t end = b[2] >= 4 && b[2] <= 127 ? b[2] : 4;
		for (size_t i = 4; i < end; i += 1 + (b[i] & 0x1f)) {
			size_t len = b[i] & 0x1f;
			if ((b[i] >> 5) != 1 || i + 1 + len > end)
				continue;
			for (size_t sad = i + 1; sad + 3 <= i + 1 + len; sad += 3)
				if (((b[sad] >> 3) & 0x0f) == 15 && (b[sad + 2] >> 3) == 12)
					return true;
		}
	}
	return false;
}

static bool sinkDecodes(AVCodecID codec)
{
	switch (codec) {
	case AV_CODEC_ID_AC3: return !sinkLine("AC-3").empty();
	case AV_CODEC_ID_EAC3: return !sinkLine("Dobly_Digital+").empty();
	case AV_CODEC_ID_DTS: return !sinkLine("DTS").empty();
	case AV_CODEC_ID_TRUEHD: return !sinkLine("MAT").empty();
	case AV_CODEC_ID_AC4: return sinkAc4();
	default: return false;
	}
}

/* high bitrate audio: the sink takes the codec at 192 kHz */
static bool sinkHbr(AVCodecID codec)
{
	std::string line = sinkLine(codec == AV_CODEC_ID_TRUEHD ? "MAT" : "DTS-HD");
	return line.find("192") != std::string::npos;
}

/* audio output: ALSA PCM, kernel AV sync controls and the volume */
class Output
{
	snd_pcm_t *m_pcm = nullptr;
	snd_ctl_t *m_ctl = nullptr;
	snd_ctl_elem_value_t *m_ptsElem = nullptr;
	snd_ctl_elem_value_t *m_masterElem = nullptr;
	std::string m_device;
	unsigned int m_rate = 0;
	unsigned int m_channels = 0;
	int m_format = -1;
	bool m_needPts = true;

	/* a media player (AUDIO_SOURCE_MEMORY) has no demux PCR: tsync runs a
	 * free system clock and the driver paces against it ("Audio Sync Clock" 1) */
	bool m_sysclock = false;
	bool m_free = false;
	std::string m_pcrOffset;
	std::string m_autoPcrOffset;
	std::string m_prefix;
	/* the output is muted in hardware: a bitstream takes no volume */
	bool m_hwMute = false;

	/* a device that failed to open is tried again after a while */
	std::string m_failed;
	time_t m_failedAt = 0;

	/* Bluetooth out of reach: HDMI plays until it is back */
	bool m_btFallback = false;
	time_t m_btRetryAt = 0;

	/* outputs without the kernel sync (Bluetooth) keep it here */
	bool m_synced = false;
	std::vector<int64_t> m_errs;
	/* late audio still to drop, beyond the block at hand */
	size_t m_skip = 0;
	std::vector<uint8_t> m_silence;

	/* the Bluetooth device comes with a configuration written after the
	 * first load */
	static snd_pcm_t *openBluetooth()
	{
		snd_pcm_t *pcm = nullptr;
		if (snd_pcm_open(&pcm, "dreambt", SND_PCM_STREAM_PLAYBACK, 0) < 0) {
			snd_config_update_free_global();
			if (snd_pcm_open(&pcm, "dreambt", SND_PCM_STREAM_PLAYBACK, 0) < 0)
				return nullptr;
		}
		return pcm;
	}

	void setControlInt(const char *name, long value)
	{
		if (!m_ctl)
			return;
		snd_ctl_elem_value_t *v;
		snd_ctl_elem_value_alloca(&v);
		snd_ctl_elem_value_set_interface(v, SND_CTL_ELEM_IFACE_MIXER);
		snd_ctl_elem_value_set_name(v, name);
		snd_ctl_elem_value_set_integer(v, 0, value);
		snd_ctl_elem_write(m_ctl, v);
	}

	void setControl(const char *name, int value)
	{
		snd_ctl_elem_value_t *v;
		snd_ctl_elem_value_alloca(&v);
		snd_ctl_elem_value_set_interface(v, SND_CTL_ELEM_IFACE_MIXER);
		snd_ctl_elem_value_set_name(v, name);
		snd_ctl_elem_value_set_enumerated(v, 0, value);
		snd_ctl_elem_write(m_ctl, v);
	}

	static snd_ctl_elem_value_t *element(const char *name)
	{
		snd_ctl_elem_value_t *v;
		snd_ctl_elem_value_malloc(&v);
		snd_ctl_elem_value_set_interface(v, SND_CTL_ELEM_IFACE_MIXER);
		snd_ctl_elem_value_set_name(v, name);
		return v;
	}

public:
	Output()
	{
		if (snd_ctl_open(&m_ctl, "hw:0", 0) < 0) {
			m_ctl = nullptr;
			LOG("no ALSA control device");
			return;
		}
		m_masterElem = element("Master");
		/* a mute left by an earlier run */
		muteSpdif(false);
	}

	~Output()
	{
		close();
		if (m_ptsElem)
			snd_ctl_elem_value_free(m_ptsElem);
		if (m_masterElem)
			snd_ctl_elem_value_free(m_masterElem);
		if (m_ctl)
			snd_ctl_close(m_ctl);
	}

	/* S/PDIF data type, 0 = PCM; sets the clock for E-AC-3 at hw_params
	 * and tells HDMI */
	void setFormat(int codec)
	{
		char value[16];
		snprintf(value, sizeof(value), "%d\n", codec);
		writeFile("/sys/class/audiodsp/digital_codec", value);
		if (m_ctl) {
			setControl("Audio spdif format", codec);
			setControl("Audio spdif_b format", codec);
		}
	}

	/* 0: HDMI, 1: S/PDIF, 2: Bluetooth */
	static int port()
	{
		return readInt("/sys/class/amhdmitx/amhdmitx0/audio_source", 0);
	}

	int open(unsigned int rate, int format, unsigned int channels = 2)
	{
		int p = port();
		struct timespec now;
		clock_gettime(CLOCK_MONOTONIC, &now);
		if (p == 2 && m_btFallback) {
			snd_pcm_t *bt = nullptr;
			if (now.tv_sec >= m_btRetryAt && (bt = openBluetooth())) {
				snd_pcm_close(bt);
				m_btFallback = false;
				LOG("Bluetooth back, audio on Bluetooth");
			} else {
				if (now.tv_sec >= m_btRetryAt)
					m_btRetryAt = now.tv_sec + 5;
				p = 0;
			}
		}
		const char *device = p == 1 ? "hw:0,1" : p == 2 ? "dreambt" : "hw:0,0";
		const char *prefix = p == 1 ? "SPDIF " : "";

		if (m_pcm && m_device == device && m_rate == rate && m_format == format &&
		    m_channels == channels)
			return 0;
		close();
		if (m_failed == device && now.tv_sec - m_failedAt < 2)
			return -ENODEV;
		setFormat(format);
		writeFile("/sys/class/amhdmitx/amhdmitx0/config", p == 0 ? "audio_on" : "audio_off");

		int err = 0;
		if (p == 2) {
			m_pcm = openBluetooth();
			if (!m_pcm) {
				LOG("Bluetooth out of reach, audio on HDMI");
				m_btFallback = true;
				m_btRetryAt = now.tv_sec + 5;
				return open(rate, format, channels);
			}
		} else {
			err = snd_pcm_open(&m_pcm, device, SND_PCM_STREAM_PLAYBACK, 0);
		}
		if (err < 0) {
			if (m_failed != device)
				LOG("open %s: %s", device, snd_strerror(err));
			m_failed = device;
			m_failedAt = now.tv_sec;
			m_pcm = nullptr;
			return err;
		}
		m_failed.clear();
		snd_pcm_hw_params_t *hw;
		snd_pcm_hw_params_alloca(&hw);
		snd_pcm_hw_params_any(m_pcm, hw);
		snd_pcm_hw_params_set_access(m_pcm, hw, SND_PCM_ACCESS_RW_INTERLEAVED);
		snd_pcm_hw_params_set_format(m_pcm, hw, SND_PCM_FORMAT_S16_LE);
		/* the S/PDIF outputs take two channels: no eight channel high
		 * bitrate audio there */
		err = snd_pcm_hw_params_set_channels(m_pcm, hw, channels);
		if (err < 0) {
			LOG("%s takes no %u channels", device, channels);
			close();
			return err;
		}
		snd_pcm_hw_params_set_rate(m_pcm, hw, rate, 0);
		snd_pcm_uframes_t period = (snd_pcm_uframes_t)rate * 1024 / 48000;
		/* Bluetooth holds the whole lead of the stream (up to ~1 s);
		 * a full buffer would delay the audio by its own size */
		snd_pcm_uframes_t buffer = period * (p == 2 || m_free ? 64 : 16);
		snd_pcm_hw_params_set_period_size_near(m_pcm, hw, &period, nullptr);
		snd_pcm_hw_params_set_buffer_size_near(m_pcm, hw, &buffer);
		err = snd_pcm_hw_params(m_pcm, hw);
		if (err < 0) {
			LOG("hw_params %s %u Hz: %s", device, rate, snd_strerror(err));
			close();
			return err;
		}
		snd_pcm_sw_params_t *sw;
		snd_pcm_sw_params_alloca(&sw);
		snd_pcm_sw_params_current(m_pcm, sw);
		/* free play has no PTS to hold the start by: a reserve against a
		 * live source delivering about a second at once (DAB+); a full
		 * buffer holds the source back, refilled after an underrun */
		snd_pcm_sw_params_set_start_threshold(m_pcm, sw, m_free ? buffer - buffer / 4 : 1);
		snd_pcm_sw_params_set_stop_threshold(m_pcm, sw, buffer);
		snd_pcm_sw_params(m_pcm, sw);

		m_device = device;
		m_rate = rate;
		m_format = format;
		m_channels = channels;
		m_needPts = true;
		m_synced = false;
		m_skip = 0;

		if (m_ptsElem)
			snd_ctl_elem_value_free(m_ptsElem);
		m_ptsElem = nullptr;
		if (m_ctl && p != 2) {
			std::string name = std::string(prefix) + "Audio Sync PTS";
			m_ptsElem = element(name.c_str());
			if (snd_ctl_elem_read(m_ctl, m_ptsElem) < 0) {
				snd_ctl_elem_value_free(m_ptsElem);
				m_ptsElem = nullptr;
			}
		}
		m_prefix = prefix;
		if (m_ptsElem)
			setControlInt((m_prefix + "Audio Sync Clock").c_str(), m_sysclock ? 1 : 0);
		LOG("output %s %u Hz %u ch %s%s%s", device, rate, channels, format ? "passthrough" : "PCM",
		    m_ptsElem ? ", paced by the kernel" : "", m_ptsElem && m_sysclock ? " (system clock)" : "");
		return 0;
	}

	/* closing the output switches the driver back to the demux PCR */
	/* the player clocks the audio itself (AUDIO_SET_AV_SYNC off): no PTS
	 * to the kernel, the output plays as it comes */
	void setFree(bool on)
	{
		if (on == m_free)
			return;
		m_free = on;
		/* a new start drops the kernel's mapping of the last PTS */
		close();
	}

	void setSysclock(bool on)
	{
		if (on == m_sysclock)
			return;
		m_sysclock = on;
		if (on) {
			m_pcrOffset = readFile("/proc/stb/pcr_offset");
			m_autoPcrOffset = readFile("/proc/stb/auto_pcr_offset");
			writeFile("/sys/class/tsync/pts_audio", "0");
			writeFile("/sys/class/tsync/pts_video", "0");
			writeFile("/sys/class/tsync/discontinue", "1");
			writeFile("/proc/stb/pcr_offset", "0x0");
			writeFile("/proc/stb/auto_pcr_offset", "0x0");
		} else {
			if (!m_pcrOffset.empty())
				writeFile("/proc/stb/pcr_offset", m_pcrOffset.c_str());
			if (!m_autoPcrOffset.empty())
				writeFile("/proc/stb/auto_pcr_offset", m_autoPcrOffset.c_str());
		}

		if (m_pcm && m_ptsElem)
			setControlInt((m_prefix + "Audio Sync Clock").c_str(), on ? 1 : 0);
	}

	void close()
	{
		setHwMute(false);
		if (m_pcm) {
			snd_pcm_drop(m_pcm);
			snd_pcm_close(m_pcm);
			m_pcm = nullptr;
		}
		m_device.clear();
		m_format = -1;
	}


	unsigned int frameBytes() const { return m_channels ? m_channels * 2 : 4; }

	/* both S/PDIF outputs, the HDMI and the optical one, send zeros */
	void muteSpdif(bool on)
	{
		setControlInt("Audio spdif mute", on);
		setControlInt("Audio spdif_b mute", on);
	}

	void setHwMute(bool on)
	{
		if (on == m_hwMute)
			return;
		m_hwMute = on;
		muteSpdif(on);
		LOG("passthrough %s", on ? "muted" : "unmuted");
	}

	/* volume of the softvol Master control the player sets */
	double gain()
	{
		/* the softvol of the Bluetooth device applies it */
		if (m_device == "dreambt")
			return 1.0;
		if (!m_masterElem || snd_ctl_elem_read(m_ctl, m_masterElem) < 0)
			return 1.0;
		long v = snd_ctl_elem_value_get_integer(m_masterElem, 0);
		if (v <= 0)
			return 0.0;
		return pow(10.0, (-51.0 + 0.2 * v) / 20.0);
	}

	void flush()
	{
		if (m_pcm) {
			snd_pcm_drop(m_pcm);
			snd_pcm_prepare(m_pcm);
		}
		m_needPts = true;
		m_synced = false;
		m_skip = 0;
	}

	/* silence ahead of the next write */
	void writeSilence(size_t frames)
	{
		m_silence.assign(frames * frameBytes(), 0);
		writeFrames(m_silence.data(), frames);
	}

	/* the kernel's sync target: due when the system clock reaches the PTS
	 * plus 8 ms and the PCM delay; the error in us, late is positive */
	bool syncError(int64_t pts, int64_t &err)
	{
		std::string scrText = readFile("/sys/class/tsync/pts_pcrscr");
		uint32_t scr = (uint32_t)strtoul(scrText.c_str(), nullptr, 16);
		if (!scr)
			return false;
		std::string delayText = readFile("/proc/stb/audio/audio_delay_pcm");
		int32_t delayPcm = (int32_t)strtoul(delayText.c_str(), nullptr, 16);
		snd_pcm_sframes_t queued = 0;
		if (snd_pcm_state(m_pcm) != SND_PCM_STATE_RUNNING || snd_pcm_delay(m_pcm, &queued) < 0)
			queued = 0;
		int64_t due = (int64_t)(int32_t)((uint32_t)pts - scr) * 100 / 9 + 8000 +
			(int64_t)delayPcm * 100 / 9;
		if (due > 5000000 || due < -5000000)
			return false;
		err = (int64_t)queued * 1000000 / m_rate - due;
		return true;
	}

	/* start in place, then correct what stays off by more than the
	 * Bluetooth latency jitter */
	void softSync(const uint8_t *&data, size_t &frames, int64_t pts)
	{
		int64_t err;
		if (!syncError(pts, err))
			return;
		int64_t fix = 0;
		if (!m_synced) {
			m_synced = true;
			m_errs.clear();
			m_skip = 0;
			fix = err;
			DBG("Bluetooth sync start %lld us", (long long)err);
		} else {
			/* the median: single blocks are often far off */
			m_errs.push_back(err);
			if (m_errs.size() < 50)
				return;
			std::nth_element(m_errs.begin(), m_errs.begin() + m_errs.size() / 2, m_errs.end());
			fix = m_errs[m_errs.size() / 2];
			m_errs.clear();
			if (fix > -25000 && fix < 25000)
				return;
			LOG("Bluetooth audio %lld ms %s", (long long)(fix > 0 ? fix : -fix) / 1000,
			    fix > 0 ? "late" : "early");
		}
		size_t n = (size_t)((fix > 0 ? fix : -fix) * m_rate / 1000000);
		if (fix > 0) {
			m_skip = n;
		} else if (n) {
			writeSilence(n < m_rate * 2 ? n : m_rate * 2);
		}
	}

	/* the driver holds, drops or stretches by the PTS; data before the
	 * first PTS after a (re)start cannot be placed */
	void write(const uint8_t *data, size_t frames, int64_t pts)
	{
		if (!m_pcm)
			return;
		/* the AV receiver controls the volume of a bitstream, the mute
		 * of the player still applies */
		setHwMute(m_format > 0 && gain() <= 0.0);
		if (m_free)
			pts = NOPTS;
		else if (pts == NOPTS && m_needPts)
			return;
		if (pts != NOPTS && m_ptsElem) {
			snd_ctl_elem_value_set_integer64(m_ptsElem, 0, pts & 0x1ffffffffLL);
			snd_ctl_elem_write(m_ctl, m_ptsElem);
		} else if (pts != NOPTS && !m_format) {
			softSync(data, frames, pts);
		}
		if (m_skip) {
			size_t n = m_skip < frames ? m_skip : frames;
			data += n * frameBytes();
			frames -= n;
			m_skip -= n;
		}
		m_needPts = false;
		writeFrames(data, frames);
	}

	void writeFrames(const uint8_t *data, size_t frames)
	{
		while (frames > 0 && !g_quit) {
			snd_pcm_sframes_t n = snd_pcm_writei(m_pcm, data, frames);
			if (n > 0) {
				data += n * m_channels * 2;
				frames -= n;
				continue;
			}
			if (n == -EAGAIN)
				continue;
			DBG("writei: %s", snd_strerror(n));
			if (snd_pcm_recover(m_pcm, n, 1) < 0) {
				close();
				return;
			}
			m_needPts = true;
			m_synced = false;
			return;
		}
	}
};

/* the transport stream of one audio PID: PES, frames, decode or pack */
class Stream
{
	Output &m_out;
	int m_bypass = -1;
	int m_channel = AUDIO_STEREO;

	std::vector<uint8_t> m_pesHeader;
	bool m_inPes = false;
	int64_t m_pesPts = NOPTS;
	int m_cc = -1;

	AVCodecID m_codec = AV_CODEC_ID_NONE;
	bool m_passthrough = false;
	bool m_hbr = false;
	int m_port = -1;
	/* when the codec last gave frames; a misprobed stream gives none */
	int64_t m_lastFrames = 0;
	AVCodecParserContext *m_parser = nullptr;
	AVCodecContext *m_parseCtx = nullptr;
	AVCodecContext *m_dec = nullptr;
	SwrContext *m_swr = nullptr;
	AVChannelLayout m_swrLayout = {};
	int m_swrRate = 0;
	AVSampleFormat m_swrFormat = AV_SAMPLE_FMT_NONE;
	AVFrame *m_frame = nullptr;
	AVPacket *m_pkt = nullptr;
	std::vector<int16_t> m_pcm;

	AVFormatContext *m_spdif = nullptr;
	std::vector<uint8_t> m_burst;
	int64_t m_burstPts = NOPTS;

	/* AAC or E-AC-3 to AC-3 for receivers without them */
	bool m_transcode = false;
	AVCodecContext *m_enc = nullptr;
	SwrContext *m_encSwr = nullptr;
	AVAudioFifo *m_encFifo = nullptr;
	AVFrame *m_encFrame = nullptr;
	AVPacket *m_encPkt = nullptr;
	int64_t m_encPts = NOPTS;

	/* stream data before the codec is known */
	std::vector<std::pair<std::vector<uint8_t>, int64_t>> m_probeChunks;
	std::vector<uint8_t> m_probeData;

	/* PCM of a player, as the Broadcom decoders take it: each PES starts
	 * with "BCMA", the size and a WAVEFORMAT block */
	std::vector<uint8_t> m_pcmIn;
	std::vector<int32_t> m_pcm24;
	unsigned int m_pcmRate = 0;
	unsigned int m_pcmChannels = 0;
	unsigned int m_pcmDepth = 0;
	bool m_pcmLe = true;
	int64_t m_pcmPts = NOPTS;
	AVFrame *m_pcmFrame = nullptr;

	/* DAB+ sends HE-AAC with 960 sample frames as LATM: FFmpeg has no SBR
	 * for that frame length, FAAD2 has */
	NeAACDecHandle m_faad = nullptr;
	std::vector<uint8_t> m_latmAsc;
	std::vector<uint8_t> m_latmPayload;
	bool m_latmFrame960 = false;
	AVFrame *m_faadFrame = nullptr;

	/* AC-4 sync frames: FFmpeg has no parser for them */
	std::vector<uint8_t> m_ac4;
	std::vector<std::pair<size_t, int64_t>> m_ac4Pts;
	std::vector<uint8_t> m_ac4Out;
	unsigned int m_ac4Phase = 0;

	/* the codec of the stream, as FFmpeg's raw demuxers see it */
	static AVCodecID probe(const std::vector<uint8_t> &buf)
	{
		static const struct
		{
			const char *name;
			AVCodecID codec;
		} formats[] = {
			{"ac3", AV_CODEC_ID_AC3},
			{"eac3", AV_CODEC_ID_EAC3},
			{"ac4", AV_CODEC_ID_AC4},
			{"aac", AV_CODEC_ID_AAC},
			{"loas", AV_CODEC_ID_AAC_LATM},
			{"mp3", AV_CODEC_ID_MP2},
			{"dts", AV_CODEC_ID_DTS},
			{"dtshd", AV_CODEC_ID_DTS},
			{"truehd", AV_CODEC_ID_TRUEHD},
			{"mlp", AV_CODEC_ID_TRUEHD},
		};
		std::vector<uint8_t> data(buf);
		data.resize(buf.size() + AVPROBE_PADDING_SIZE);
		AVProbeData pd = {};
		pd.filename = "";
		pd.buf = data.data();
		pd.buf_size = (int)buf.size();
		/* a weak guess waits for more data */
		int score = 0;
		const AVInputFormat *fmt = av_probe_input_format3(&pd, 1, &score);
		if (!fmt || score < AVPROBE_SCORE_MAX / 4)
			return AV_CODEC_ID_NONE;
		for (const auto &f : formats)
			if (!strcmp(fmt->name, f.name)) {
				if (f.codec == AV_CODEC_ID_MP2)
					return mpegLayer(buf);
				return f.codec;
			}
		DBG("unknown stream format %s", fmt->name);
		return AV_CODEC_ID_NONE;
	}

	/* two MPEG audio frame headers of the same stream a frame apart */
	static bool mpegSync(const std::vector<uint8_t> &buf)
	{
		static const int rates[3] = {44100, 48000, 32000};
		static const int kbps[3][15] = {
			{0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448},
			{0, 32, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384},
			{0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320},
		};

		for (size_t i = 0; i + 4 <= buf.size(); i++) {
			if (buf[i] != 0xff || (buf[i + 1] & 0xf8) != 0xf8 || !(buf[i + 1] & 0x06))
				continue;
			int layer = 3 - ((buf[i + 1] >> 1) & 3);
			int bitrate = buf[i + 2] >> 4;
			int rate = (buf[i + 2] >> 2) & 3;
			if (bitrate == 0 || bitrate == 15 || rate == 3)
				continue;
			int padding = (buf[i + 2] >> 1) & 1;
			size_t size = layer == 0 ?
				(12 * kbps[0][bitrate] * 1000 / rates[rate] + padding) * 4 :
				144 * kbps[layer][bitrate] * 1000 / rates[rate] + padding;
			if (i + size + 2 > buf.size())
				return false;
			if (buf[i + size] == 0xff && (buf[i + size + 1] & 0xfe) == (buf[i + 1] & 0xfe))
				return true;
		}
		return false;
	}

	/* the mp3 demuxer takes all MPEG audio layers */
	static AVCodecID mpegLayer(const std::vector<uint8_t> &buf)
	{
		for (size_t i = 0; i + 1 < buf.size(); i++)
			if (buf[i] == 0xff && (buf[i + 1] & 0xe0) == 0xe0 && (buf[i + 1] & 0x06))
				return (buf[i + 1] & 0x06) == 0x02 ? AV_CODEC_ID_MP3 :
					(buf[i + 1] & 0x06) == 0x06 ? AV_CODEC_ID_MP1 : AV_CODEC_ID_MP2;
		return AV_CODEC_ID_MP2;
	}

	/* passthrough as the settings and the sink allow */
	bool wantPassthrough(AVCodecID codec)
	{
		if (Output::port() == 2)
			return false;
		std::string override;
		switch (codec) {
		case AV_CODEC_ID_AC3: break;
		case AV_CODEC_ID_EAC3: override = g_settings.get("config.av.transcodeac3plus"); break;
		case AV_CODEC_ID_DTS: override = g_settings.get("config.av.dtshd"); break;
		case AV_CODEC_ID_TRUEHD: override = g_settings.get("config.av.truehd"); break;
		case AV_CODEC_ID_AC4: override = g_settings.get("config.av.ac4"); break;
		default: return false;
		}
		bool want = readInt("/sys/class/audiodsp/digital_raw", 0) != 0;
		if (override == "passthrough" || override == "hdmi_best")
			want = true;
		else if (override == "downmix" || override == "force_ac3")
			want = false;
		/* AC-4 also passes when asked for, the sink may sit behind a
		 * receiver that does not list it */
		if (want && Output::port() == 0 && !sinkDecodes(codec) &&
		    !(codec == AV_CODEC_ID_AC4 && override == "passthrough"))
			want = false;
		return want;
	}

	/* AC-3 instead of AAC or E-AC-3 as the settings ask */
	bool wantTranscode(AVCodecID codec)
	{
		std::string value;
		if (codec == AV_CODEC_ID_AAC || codec == AV_CODEC_ID_AAC_LATM)
			value = g_settings.get("config.av.transcodeaac");
		else if (codec == AV_CODEC_ID_EAC3)
			value = g_settings.get("config.av.transcodeac3plus");
		if (value != "force_ac3" || Output::port() == 2)
			return false;
		return Output::port() == 1 || sinkDecodes(AV_CODEC_ID_AC3);
	}

	/* the driver's S/PDIF formats; HDMI sends high bitrate packets only for
	 * TrueHD and DTS-HD MA, and DTS RAW Mode would announce PCM */
	static int spdifCodec(AVCodecID codec, bool hbr)
	{
		switch (codec) {
		case AV_CODEC_ID_AC3: return 2;
		case AV_CODEC_ID_EAC3: return 4;
		case AV_CODEC_ID_DTS: return hbr ? 8 : 3;
		case AV_CODEC_ID_TRUEHD: return 7;
		default: return 0;
		}
	}

	static int burstWrite(void *opaque, const uint8_t *buf, int size)
	{
		Stream *s = (Stream *)opaque;
		s->m_burst.insert(s->m_burst.end(), buf, buf + size);
		return size;
	}

	bool openPassthrough(AVCodecID codecId, int sampleRate)
	{
		if (avformat_alloc_output_context2(&m_spdif, nullptr, "spdif", nullptr) < 0)
			return false;
		uint8_t *buf = (uint8_t *)av_malloc(65536);
		m_spdif->pb = avio_alloc_context(buf, 65536, 1, this, nullptr, burstWrite, nullptr);
		AVStream *st = avformat_new_stream(m_spdif, nullptr);
		if (!m_spdif->pb || !st)
			return false;
		st->codecpar->codec_type = AVMEDIA_TYPE_AUDIO;
		st->codecpar->codec_id = codecId;
		st->codecpar->sample_rate = sampleRate;
		/* high bitrate audio: eight channels at 192 kHz carry TrueHD (as
		 * MAT) or DTS-HD with its extensions */
		av_channel_layout_default(&st->codecpar->ch_layout,
					  !m_hbr ? 2 : codecId == AV_CODEC_ID_TRUEHD ? 8 : 6);
		st->time_base = AVRational{1, 90000};
		AVDictionary *opts = nullptr;
		if (m_hbr && codecId == AV_CODEC_ID_DTS)
			av_dict_set(&opts, "dtshd_rate", "192000", 0);
		int ret = avformat_write_header(m_spdif, &opts);
		av_dict_free(&opts);
		if (ret < 0)
			return false;
		if (m_hbr)
			return m_out.open(192000, spdifCodec(codecId, true), 8) == 0;
		return m_out.open(48000, spdifCodec(codecId, false)) == 0;
	}

	/* a frame as IEC 61937 bursts; one burst may take several frames */
	void writeBurst(AVPacket *pkt)
	{
		m_lastFrames = nowMs();
		if (m_burst.empty() && m_burstPts == NOPTS)
			m_burstPts = pkt->pts;
		if (av_write_frame(m_spdif, pkt) < 0)
			return;
		avio_flush(m_spdif->pb);
		size_t frames = m_burst.size() / m_out.frameBytes();
		if (frames) {
			m_out.write(m_burst.data(), frames, m_burstPts);
			m_burst.erase(m_burst.begin(), m_burst.begin() + frames * m_out.frameBytes());
			m_burstPts = NOPTS;
		}
	}

	void closePassthrough()
	{
		if (!m_spdif)
			return;
		if (m_spdif->pb) {
			av_freep(&m_spdif->pb->buffer);
			avio_context_free(&m_spdif->pb);
		}
		avformat_free_context(m_spdif);
		m_spdif = nullptr;
		m_burst.clear();
		m_burstPts = NOPTS;
	}

	bool openDecoder()
	{
		const AVCodec *codec = avcodec_find_decoder(m_codec);
		if (!codec)
			return false;
		m_dec = avcodec_alloc_context3(codec);
		m_dec->pkt_timebase = AVRational{1, 90000};
		/* AC-3 keeps the surround channels */
		if (!m_transcode)
			av_opt_set(m_dec, "downmix", "stereo", AV_OPT_SEARCH_CHILDREN);
		if (m_codec == AV_CODEC_ID_AC3 || m_codec == AV_CODEC_ID_EAC3) {
			av_opt_set_int(m_dec, "target_level", -24, AV_OPT_SEARCH_CHILDREN);
			av_opt_set_int(m_dec, "heavy_compr", 1, AV_OPT_SEARCH_CHILDREN);
		}
		return avcodec_open2(m_dec, codec, nullptr) == 0;
	}

	static int64_t nowMs()
	{
		struct timespec now;
		clock_gettime(CLOCK_MONOTONIC, &now);
		return (int64_t)now.tv_sec * 1000 + now.tv_nsec / 1000000;
	}

	bool start(AVCodecID codecId)
	{
		m_codec = codecId;
		m_lastFrames = nowMs();
		if (m_codec != AV_CODEC_ID_AC4) {
			m_parser = av_parser_init(m_codec);
			if (!m_parser)
				return false;
		}
		const AVCodec *codec = avcodec_find_decoder(m_codec);
		m_parseCtx = avcodec_alloc_context3(codec);
		if (!m_parseCtx)
			return false;
		m_port = Output::port();
		m_passthrough = wantPassthrough(m_codec);
		m_hbr = m_passthrough && Output::port() == 0 &&
			(m_codec == AV_CODEC_ID_TRUEHD || m_codec == AV_CODEC_ID_DTS) && sinkHbr(m_codec);
		if (m_codec == AV_CODEC_ID_TRUEHD && m_passthrough && !m_hbr)
			m_passthrough = false;
		m_transcode = !m_passthrough && wantTranscode(m_codec);
		LOG("stream %s, %s", avcodec_get_name(m_codec),
		    m_hbr ? "high bitrate passthrough" : m_passthrough ? "passthrough" :
		    m_transcode ? "to AC-3" : "decode");
		return m_passthrough || openDecoder();
	}

	bool openEncoder(AVFrame *f)
	{
		const AVCodec *codec = avcodec_find_encoder(AV_CODEC_ID_AC3);
		if (!codec)
			return false;
		m_enc = avcodec_alloc_context3(codec);
		AVChannelLayout layout;
		if (f->ch_layout.nb_channels > 2)
			layout = AV_CHANNEL_LAYOUT_5POINT1;
		else
			layout = AV_CHANNEL_LAYOUT_STEREO;
		av_channel_layout_copy(&m_enc->ch_layout, &layout);
		m_enc->sample_fmt = AV_SAMPLE_FMT_FLTP;
		m_enc->sample_rate = 48000;
		m_enc->bit_rate = layout.nb_channels > 2 ? 448000 : 192000;
		m_enc->time_base = AVRational{1, 48000};
		if (avcodec_open2(m_enc, codec, nullptr) < 0)
			return false;
		if (swr_alloc_set_opts2(&m_encSwr, &m_enc->ch_layout, AV_SAMPLE_FMT_FLTP, 48000,
					&f->ch_layout, (AVSampleFormat)f->format, f->sample_rate, 0, nullptr) < 0)
			return false;
		av_opt_set_double(m_encSwr, "rematrix_maxval", 1.0, 0);
		if (swr_init(m_encSwr) < 0)
			return false;
		m_encFifo = av_audio_fifo_alloc(AV_SAMPLE_FMT_FLTP, m_enc->ch_layout.nb_channels,
						m_enc->frame_size * 8);
		m_encFrame = av_frame_alloc();
		m_encPkt = av_packet_alloc();
		LOG("AC-3 %d channels %d kbit/s", m_enc->ch_layout.nb_channels,
		    (int)(m_enc->bit_rate / 1000));
		return m_encFifo && m_encFrame && m_encPkt && openPassthrough(AV_CODEC_ID_AC3, 48000);
	}

	void closeEncoder()
	{
		avcodec_free_context(&m_enc);
		swr_free(&m_encSwr);
		if (m_encFifo)
			av_audio_fifo_free(m_encFifo);
		m_encFifo = nullptr;
		av_frame_free(&m_encFrame);
		av_packet_free(&m_encPkt);
		m_encPts = NOPTS;
	}

	void encodeAc3(AVFrame *f)
	{
		if (!m_enc && !openEncoder(f)) {
			LOG("AC-3 encoder failed, decoding instead");
			closeEncoder();
			closePassthrough();
			m_transcode = false;
			return;
		}
		int max = swr_get_out_samples(m_encSwr, f->nb_samples);
		std::vector<uint8_t *> planes(m_enc->ch_layout.nb_channels);
		if (av_samples_alloc(planes.data(), nullptr, m_enc->ch_layout.nb_channels, max,
				     AV_SAMPLE_FMT_FLTP, 0) < 0)
			return;
		int n = swr_convert(m_encSwr, planes.data(), max, (const uint8_t **)f->extended_data,
				    f->nb_samples);
		/* the PTS of the first sample waiting for the encoder */
		if (f->pts != NOPTS && av_audio_fifo_size(m_encFifo) == 0)
			m_encPts = f->pts;
		if (n > 0)
			av_audio_fifo_write(m_encFifo, (void **)planes.data(), n);
		av_freep(&planes[0]);

		int size = m_enc->frame_size;
		while (av_audio_fifo_size(m_encFifo) >= size) {
			av_frame_unref(m_encFrame);
			m_encFrame->nb_samples = size;
			m_encFrame->format = AV_SAMPLE_FMT_FLTP;
			m_encFrame->sample_rate = 48000;
			av_channel_layout_copy(&m_encFrame->ch_layout, &m_enc->ch_layout);
			if (av_frame_get_buffer(m_encFrame, 0) < 0)
				return;
			av_audio_fifo_read(m_encFifo, (void **)m_encFrame->data, size);
			int64_t pts = m_encPts;
			if (m_encPts != NOPTS)
				m_encPts += (int64_t)size * 90000 / 48000;
			if (avcodec_send_frame(m_enc, m_encFrame) < 0)
				continue;
			while (avcodec_receive_packet(m_enc, m_encPkt) == 0) {
				m_encPkt->pts = pts;
				writeBurst(m_encPkt);
				av_packet_unref(m_encPkt);
			}
		}
	}

	/* the largest stereo coefficient sum of a layout over that of 5.1 */
	static double downmixHeadroom(const AVChannelLayout &layout)
	{
		AVChannelLayout stereo = AV_CHANNEL_LAYOUT_STEREO;
		std::vector<double> matrix(2 * 64);
		if (layout.nb_channels > 64 ||
		    swr_build_matrix2(&layout, &stereo, M_SQRT1_2, M_SQRT1_2, 0, 1e9, 1.0,
				      matrix.data(), 64, AV_MATRIX_ENCODING_NONE, nullptr) < 0)
			return 1.0;
		double sum = 0;
		for (int out = 0; out < 2; out++) {
			double row = 0;
			for (int in = 0; in < layout.nb_channels; in++)
				row += fabs(matrix[out * 64 + in]);
			sum = row > sum ? row : sum;
		}
		double sum51 = 1 + 2 * M_SQRT1_2;
		return sum > sum51 ? sum / sum51 : 1.0;
	}

	void pushPcm(AVFrame *f)
	{
		m_lastFrames = nowMs();
		if (m_transcode) {
			encodeAc3(f);
			if (m_transcode)
				return;
		}
		int rate = f->sample_rate;
		/* the output runs at the common rates only (AC-4 at video frame
		 * rates decodes to 51.2 kHz) */
		int outRate = rate == 32000 || rate == 44100 || rate == 48000 ? rate : 48000;
		if (!m_swr || m_swrRate != rate || m_swrFormat != f->format ||
		    av_channel_layout_compare(&m_swrLayout, &f->ch_layout)) {
			swr_free(&m_swr);
			AVChannelLayout stereo = AV_CHANNEL_LAYOUT_STEREO;
			if (swr_alloc_set_opts2(&m_swr, &stereo, AV_SAMPLE_FMT_S16, outRate,
						&f->ch_layout, (AVSampleFormat)f->format, rate, 0, nullptr) < 0)
				return;
			/* no clipping when 5.1 is downmixed; more channels (AC-4 7.1.4)
			 * come out as loud as 5.1 would */
			av_opt_set_double(m_swr, "rematrix_maxval", downmixHeadroom(f->ch_layout), 0);
			if (swr_init(m_swr) < 0) {
				swr_free(&m_swr);
				return;
			}
			char layout[64];
			av_channel_layout_describe(&f->ch_layout, layout, sizeof(layout));
			DBG("PCM from %s %d Hz", layout, rate);
			av_channel_layout_uninit(&m_swrLayout);
			av_channel_layout_copy(&m_swrLayout, &f->ch_layout);
			m_swrRate = rate;
			m_swrFormat = (AVSampleFormat)f->format;
		}
		if (m_out.open(outRate, 0) < 0)
			return;
		int max = swr_get_out_samples(m_swr, f->nb_samples);
		m_pcm.resize((size_t)max * 2);
		uint8_t *out = (uint8_t *)m_pcm.data();
		int n = swr_convert(m_swr, &out, max, (const uint8_t **)f->extended_data, f->nb_samples);
		if (n <= 0)
			return;

		double gain = m_out.gain();
		for (int i = 0; i < n; i++) {
			int16_t l = m_pcm[i * 2], r = m_pcm[i * 2 + 1];
			if (m_channel == AUDIO_MONO_LEFT)
				r = l;
			else if (m_channel == AUDIO_MONO_RIGHT)
				l = r;
			if (gain < 1.0) {
				l = (int16_t)lrint(l * gain);
				r = (int16_t)lrint(r * gain);
			}
			m_pcm[i * 2] = l;
			m_pcm[i * 2 + 1] = r;
		}
		m_out.write((const uint8_t *)m_pcm.data(), n, f->pts);
	}

	struct BitReader
	{
		const uint8_t *p;
		size_t n;
		size_t bit = 0;

		unsigned int read(int bits)
		{
			unsigned int v = 0;
			for (int i = 0; i < bits; i++, bit++)
				v = (v << 1) | (bit / 8 < n ? (p[bit / 8] >> (7 - bit % 8)) & 1 : 0);
			return v;
		}

		bool ok() const { return bit <= n * 8; }
	};

	static void copyBits(BitReader &r, size_t bits, std::vector<uint8_t> &out)
	{
		out.assign((bits + 7) / 8, 0);
		for (size_t i = 0; i < bits; i++)
			if (r.read(1))
				out[i / 8] |= 0x80 >> (i % 8);
	}

	/* the AudioSpecificConfig of a StreamMuxConfig with one program and one
	 * layer of AAC (HE-AAC v1/v2 on an AAC LC core), false for other forms */
	bool latmConfig(BitReader &r)
	{
		if (r.read(1))			/* audioMuxVersion */
			return false;
		r.read(1);			/* allStreamsSameTimeFraming */
		if (r.read(6) || r.read(4) || r.read(3))	/* subframes, programs, layers */
			return false;
		size_t start = r.bit;
		unsigned int aot = r.read(5);
		if (aot == 31)
			aot = 32 + r.read(6);
		if (r.read(4) == 15)
			r.read(24);
		if (!r.read(4))			/* channel configuration from a PCE */
			return false;
		if (aot == 5 || aot == 29) {
			if (r.read(4) == 15)
				r.read(24);
			aot = r.read(5);
			if (aot == 31)
				aot = 32 + r.read(6);
		}
		if (aot != 2)
			return false;
		bool frame960 = r.read(1);
		if (r.read(1))			/* dependsOnCoreCoder */
			r.read(14);
		if (r.read(1))			/* extensionFlag */
			return false;
		size_t end = r.bit;
		if (r.read(3))			/* frameLengthType */
			return false;
		r.read(8);			/* latmBufferFullness */
		if (r.read(1))			/* otherDataPresent */
			return false;
		if (r.read(1))			/* crcCheckPresent */
			r.read(8);
		if (!r.ok())
			return false;
		BitReader asc{r.p, r.n, start};
		copyBits(asc, end - start, m_latmAsc);
		m_latmFrame960 = frame960;
		return true;
	}

	bool openFaad()
	{
		m_faad = NeAACDecOpen();
		if (!m_faad)
			return false;
		NeAACDecConfigurationPtr config = NeAACDecGetCurrentConfiguration(m_faad);
		config->outputFormat = FAAD_FMT_16BIT;
		config->downMatrix = 1;
		NeAACDecSetConfiguration(m_faad, config);
		unsigned long rate = 0;
		unsigned char channels = 0;
		if (NeAACDecInit2(m_faad, m_latmAsc.data(), m_latmAsc.size(), &rate, &channels) < 0) {
			closeFaad();
			return false;
		}
		LOG("AAC with 960 sample frames through FAAD2, %lu Hz %u ch", rate, channels);
		return true;
	}

	void closeFaad()
	{
		if (m_faad)
			NeAACDecClose(m_faad);
		m_faad = nullptr;
	}

	/* a LOAS frame of AAC with 960 sample frames to FAAD2; false when it is
	 * none and FFmpeg decodes it */
	bool faadLatm(AVPacket *pkt)
	{
		BitReader r{pkt->data, (size_t)pkt->size};
		if (pkt->size < 4 || r.read(11) != 0x2b7)
			return false;
		r.read(13);			/* audioMuxLengthBytes */
		if (!r.read(1)) {		/* useSameStreamMux */
			std::vector<uint8_t> asc = m_latmAsc;
			if (!latmConfig(r)) {
				closeFaad();
				m_latmAsc.clear();
				return false;
			}
			if (m_latmAsc != asc)
				closeFaad();
		}
		if (m_latmAsc.empty() || !m_latmFrame960)
			return false;
		if (!m_faad && !openFaad())
			return false;
		size_t len = 0;
		unsigned int v;
		do {
			v = r.read(8);
			len += v;
		} while (v == 255 && r.ok());
		if (!r.ok() || r.bit + len * 8 > (size_t)pkt->size * 8)
			return true;
		copyBits(r, len * 8, m_latmPayload);
		NeAACDecFrameInfo info;
		void *pcm = NeAACDecDecode(m_faad, &info, m_latmPayload.data(), (unsigned long)m_latmPayload.size());
		if (!pcm || info.error || !info.samples || !info.channels)
			return true;
		if (!m_faadFrame)
			m_faadFrame = av_frame_alloc();
		AVFrame *f = m_faadFrame;
		f->format = AV_SAMPLE_FMT_S16;
		f->sample_rate = (int)info.samplerate;
		av_channel_layout_uninit(&f->ch_layout);
		av_channel_layout_default(&f->ch_layout, info.channels);
		f->nb_samples = (int)(info.samples / info.channels);
		f->data[0] = (uint8_t *)pcm;
		f->extended_data = f->data;
		f->pts = pkt->pts;
		pushPcm(f);
		return true;
	}

	void onFrame(AVPacket *pkt)
	{
		if (m_codec == AV_CODEC_ID_AAC_LATM && !m_passthrough && faadLatm(pkt))
			return;
		if (m_passthrough && !m_spdif) {
			int rate = m_parseCtx->sample_rate ? m_parseCtx->sample_rate : 48000;
			bool open = openPassthrough(m_codec, rate);
			/* no high bitrate output: DTS passes its core as plain DTS,
			 * TrueHD is decoded */
			if (!open && m_hbr) {
				closePassthrough();
				m_hbr = false;
				if (m_codec == AV_CODEC_ID_DTS) {
					LOG("stream dts, passthrough of the core");
					open = openPassthrough(m_codec, rate);
				}
			}
			if (!open) {
				closePassthrough();
				m_passthrough = false;
				if (!openDecoder())
					return;
			}
		}
		if (m_passthrough) {
			writeBurst(pkt);
			return;
		}
		if (!m_dec || avcodec_send_packet(m_dec, pkt) < 0)
			return;
		while (avcodec_receive_frame(m_dec, m_frame) == 0) {
			pushPcm(m_frame);
			av_frame_unref(m_frame);
		}
	}

	/* frame rate of an AC-4 frame from its TOC (ETSI TS 103 190-1 4.2.3.1),
	 * -1 if it cannot be read; 44.1 kHz streams set rate44 */
	static int ac4FrameRate(const uint8_t *p, size_t n, bool &rate44)
	{
		size_t bit = 0;
		auto read = [&](int bits) {
			unsigned int v = 0;
			for (int i = 0; i < bits; i++, bit++)
				v = (v << 1) | (bit / 8 < n ? (p[bit / 8] >> (7 - bit % 8)) & 1 : 0);
			return v;
		};
		unsigned int version = read(2);
		if (version == 3) {
			bool more;
			do {
				read(2);
				more = read(1);
			} while (more && bit < 64);
		}
		read(10);			/* sequence_counter */
		if (read(1) && read(3))		/* b_wait_frames, wait_frames */
			read(2);		/* br_code */
		rate44 = read(1) == 0;		/* fs_index */
		int index = (int)read(4);
		return bit / 8 < n && index < 14 ? index : -1;
	}

	/* an AC-4 sync frame as an IEC 61937-14 data-burst, data type 24,
	 * repeated at the frame duration on the stereo link */
	void ac4Burst(const uint8_t *frame, size_t size, size_t header, int64_t pts)
	{
		m_lastFrames = nowMs();
		/* IEC 60958 frames per AC-4 frame at 48 kHz, as num / den */
		static const unsigned int period[14][2] = {
			{2002, 1}, {2000, 1}, {1920, 1}, {8008, 5}, {1600, 1}, {1001, 1}, {1000, 1},
			{960, 1}, {4004, 5}, {800, 1}, {480, 1}, {2002, 5}, {400, 1}, {2048, 1},
		};
		bool rate44 = false;
		int index = ac4FrameRate(frame + header, size - header, rate44);
		if (index < 0 || (rate44 && index != 13))
			return;
		if (m_out.open(rate44 ? 44100 : 48000, spdifCodec(AV_CODEC_ID_AC3, false)) < 0)
			return;
		/* NTSC rates: the periods alternate so that five bursts take the
		 * exact duration */
		unsigned int den = period[index][1];
		m_ac4Phase += period[index][0];
		size_t frames = m_ac4Phase / den;
		m_ac4Phase %= den;
		size_t words = frames * 2;
		size_t payload = (size + 1) / 2;
		/* preamble plus two IEC 60958 frames of spacing */
		if (payload + 4 + 4 > words) {
			DBG("AC-4 frame of %zu bytes too large for the burst", size);
			return;
		}
		m_ac4Out.assign(words * 2, 0);
		uint8_t *o = m_ac4Out.data();
		auto put = [&](size_t word, unsigned int v) {
			o[word * 2] = v & 0xff;
			o[word * 2 + 1] = v >> 8;
		};
		put(0, 0xf872);
		put(1, 0x4e1f);
		put(2, 24 | (index << 8));
		put(3, (unsigned int)(size * 8));
		for (size_t i = 0; i < size; i += 2)
			put(4 + i / 2, (frame[i] << 8) | (i + 1 < size ? frame[i + 1] : 0));
		m_out.write(m_ac4Out.data(), frames, pts);
	}

	/* an AC-4 sync frame takes the PTS of the PES packet it starts in */
	void onAc4(const uint8_t *p, size_t n, int64_t pts)
	{
		if (pts != NOPTS)
			m_ac4Pts.push_back({m_ac4.size(), pts});
		m_ac4.insert(m_ac4.end(), p, p + n);
		size_t pos = 0;
		for (;;) {
			while (pos + 2 <= m_ac4.size() &&
			       !(m_ac4[pos] == 0xac && (m_ac4[pos + 1] & 0xfe) == 0x40))
				pos++;
			if (pos + 7 > m_ac4.size())
				break;
			const uint8_t *q = &m_ac4[pos];
			size_t header = 4;
			size_t size = (q[2] << 8) | q[3];
			if (size == 0xffff) {
				header = 7;
				size = (q[4] << 16) | (q[5] << 8) | q[6];
			}
			size_t total = header + size + (q[1] == 0x41 ? 2 : 0);
			if (pos + total > m_ac4.size())
				break;
			int64_t framePts = NOPTS;
			while (!m_ac4Pts.empty() && m_ac4Pts.front().first <= pos) {
				framePts = m_ac4Pts.front().second;
				m_ac4Pts.erase(m_ac4Pts.begin());
			}
			if (m_passthrough) {
				ac4Burst(q, total, header, framePts);
			} else {
				m_pkt->data = (uint8_t *)q;
				m_pkt->size = (int)total;
				m_pkt->pts = framePts;
				m_pkt->dts = NOPTS;
				onFrame(m_pkt);
			}
			pos += total;
		}
		m_ac4.erase(m_ac4.begin(), m_ac4.begin() + pos);
		for (auto &e : m_ac4Pts)
			e.first = e.first > pos ? e.first - pos : 0;
	}

	static uint16_t le16(const uint8_t *p) { return p[0] | (p[1] << 8); }
	static uint32_t le32(const uint8_t *p) { return le16(p) | ((uint32_t)le16(p + 2) << 16); }

	void onBcma(const uint8_t *p, size_t n, int64_t pts)
	{
		if (n >= 26 && !memcmp(p, "BCMA", 4)) {
			const uint8_t *fmt = p + 8;
			m_pcmLe = le16(fmt) == 0x0001;
			m_pcmChannels = le16(fmt + 2);
			m_pcmRate = le32(fmt + 4);
			m_pcmDepth = le16(fmt + 14);
			if (pts != NOPTS)
				m_pcmPts = pts;
			m_pcmIn.clear();
			p += 26;
			n -= 26;
		}
		unsigned int width = m_pcmDepth / 8;
		size_t frameBytes = (size_t)width * m_pcmChannels;
		if (!frameBytes || !m_pcmRate || m_pcmChannels > 8 || width > 4)
			return;
		m_pcmIn.insert(m_pcmIn.end(), p, p + n);
		size_t frames = m_pcmIn.size() / frameBytes;
		if (!frames)
			return;

		uint8_t *data = m_pcmIn.data();
		AVSampleFormat format = AV_SAMPLE_FMT_S16;
		if (width == 1) {
			format = AV_SAMPLE_FMT_U8;
		} else if (width == 2 && !m_pcmLe) {
			for (size_t i = 0; i < frames * frameBytes; i += 2)
				std::swap(data[i], data[i + 1]);
		} else if (width == 3 || width == 4) {
			/* 24 bit to 32, both orders */
			m_pcm24.resize(frames * m_pcmChannels);
			for (size_t i = 0; i < m_pcm24.size(); i++) {
				const uint8_t *q = data + i * width;
				uint32_t v = 0;
				for (unsigned int b = 0; b < width; b++)
					v |= (uint32_t)q[m_pcmLe ? b : width - 1 - b] << (8 * (4 - width + b));
				m_pcm24[i] = (int32_t)v;
			}
			data = (uint8_t *)m_pcm24.data();
			format = AV_SAMPLE_FMT_S32;
		}

		if (!m_pcmFrame)
			m_pcmFrame = av_frame_alloc();
		AVFrame *f = m_pcmFrame;
		f->format = format;
		f->sample_rate = m_pcmRate;
		av_channel_layout_uninit(&f->ch_layout);
		av_channel_layout_default(&f->ch_layout, m_pcmChannels);
		f->nb_samples = (int)frames;
		f->data[0] = data;
		f->extended_data = f->data;
		f->pts = m_pcmPts;
		pushPcm(f);
		f->data[0] = nullptr;
		if (m_pcmPts != NOPTS)
			m_pcmPts += (int64_t)frames * 90000 / m_pcmRate;
		m_pcmIn.erase(m_pcmIn.begin(), m_pcmIn.begin() + frames * frameBytes);
	}

	void onPayload(const uint8_t *p, size_t n, int64_t pts)
	{
		if (m_codec == AV_CODEC_ID_NONE && n >= 4 && !memcmp(p, "BCMA", 4)) {
			m_codec = AV_CODEC_ID_PCM_S16LE;
			m_lastFrames = nowMs();
			m_port = Output::port();
			LOG("stream pcm");
		}
		if (m_codec == AV_CODEC_ID_NONE) {
			/* collect a few KB for FFmpeg to tell the codec */
			m_probeChunks.push_back({std::vector<uint8_t>(p, p + n), pts});
			m_probeData.insert(m_probeData.end(), p, p + n);
			/* the player names MPEG audio with bypass 1: FFmpeg needs seconds
			 * of it for a sure guess */
			bool mpeg = m_bypass == 1 && mpegSync(m_probeData);
			if (m_probeData.size() < 4096 && !mpeg)
				return;
			AVCodecID codec = mpeg ? mpegLayer(m_probeData) : probe(m_probeData);
			if (codec == AV_CODEC_ID_NONE) {
				if (m_probeData.size() > 65536) {
					LOG("unknown audio stream");
					m_probeChunks.clear();
					m_probeData.clear();
				}
				return;
			}
			auto chunks = std::move(m_probeChunks);
			m_probeChunks.clear();
			m_probeData.clear();
			if (!start(codec)) {
				LOG("no %s support", avcodec_get_name(codec));
				resetCodec();
				return;
			}
			for (auto &c : chunks)
				decodePayload(c.first.data(), c.first.size(), c.second);
			return;
		}
		decodePayload(p, n, pts);
	}

	void decodePayload(const uint8_t *p, size_t n, int64_t pts)
	{
		if (nowMs() - m_lastFrames > 2000) {
			LOG("no frames from %s, probing again", avcodec_get_name(m_codec));
			resetCodec();
			onPayload(p, n, pts);
			return;
		}
		if (m_codec == AV_CODEC_ID_AC4) {
			onAc4(p, n, pts);
			return;
		}
		if (m_codec == AV_CODEC_ID_PCM_S16LE) {
			onBcma(p, n, pts);
			return;
		}
		while (n > 0) {
			uint8_t *data = nullptr;
			int size = 0;
			int used = av_parser_parse2(m_parser, m_parseCtx, &data, &size, p, (int)n, pts, NOPTS, 0);
			if (used < 0)
				return;
			p += used;
			n -= used;
			pts = NOPTS;
			if (size > 0) {
				m_pkt->data = data;
				m_pkt->size = size;
				m_pkt->pts = m_parser->pts;
				m_pkt->dts = NOPTS;
				onFrame(m_pkt);
			}
		}
	}

	bool onPes(const uint8_t *p, size_t n)
	{
		/* PES header: start code, stream id, length, flags, header length */
		if (n < 9 || p[0] || p[1] || p[2] != 1)
			return false;
		size_t header = 9 + p[8];
		if (header > n)
			return false;
		m_pesPts = NOPTS;
		if ((p[7] & 0x80) && n >= 14)
			m_pesPts = ((int64_t)(p[9] & 0x0e) << 29) | (p[10] << 22) |
				((p[11] & 0xfe) << 14) | (p[12] << 7) | (p[13] >> 1);
		/* passthrough and transcoding depend on the output */
		if (m_codec != AV_CODEC_ID_NONE && Output::port() != m_port)
			resetCodec();
		/* a player writes the header apart from the payload: the PTS
		 * goes with the payload that follows */
		if (header < n) {
			onPayload(p + header, n - header, m_pesPts);
			m_pesPts = NOPTS;
		}
		return true;
	}

	void resetCodec()
	{
		closeEncoder();
		closePassthrough();
		if (m_parser)
			av_parser_close(m_parser);
		m_parser = nullptr;
		avcodec_free_context(&m_parseCtx);
		avcodec_free_context(&m_dec);
		swr_free(&m_swr);
		av_channel_layout_uninit(&m_swrLayout);
		m_swrRate = 0;
		m_codec = AV_CODEC_ID_NONE;
		closeFaad();
		m_latmAsc.clear();
		m_probeChunks.clear();
		m_probeData.clear();
		m_ac4.clear();
		m_ac4Pts.clear();
		m_pcmIn.clear();
		m_pcmPts = NOPTS;
		m_pcmRate = 0;
		m_ac4Phase = 0;
		m_passthrough = false;
		m_hbr = false;
		m_transcode = false;
	}

public:
	explicit Stream(Output &out) : m_out(out)
	{
		m_frame = av_frame_alloc();
		m_pkt = av_packet_alloc();
	}

	~Stream()
	{
		resetCodec();
		av_frame_free(&m_frame);
		av_packet_free(&m_pkt);
		if (m_pcmFrame)
			av_channel_layout_uninit(&m_pcmFrame->ch_layout);
		av_frame_free(&m_pcmFrame);
		if (m_faadFrame)
			av_channel_layout_uninit(&m_faadFrame->ch_layout);
		av_frame_free(&m_faadFrame);
	}

	void setBypass(int bypass)
	{
		if (bypass != m_bypass)
			reset();
		m_bypass = bypass;
	}

	void setChannel(int channel) { m_channel = channel; }

	/* a pause is no stream without frames */
	void resume() { m_lastFrames = nowMs(); }

	/* a new stream: parser, decoder and output start over */
	void reset()
	{
		resetCodec();
		m_pesHeader.clear();
		m_inPes = false;
		m_cc = -1;
		m_out.flush();
	}

	void feed(const uint8_t *data, size_t n)
	{
		for (; n >= TS_SIZE; data += TS_SIZE, n -= TS_SIZE) {
			const uint8_t *ts = data;
			if (ts[0] != 0x47 || (ts[1] & 0x80))
				continue;
			int afc = (ts[3] >> 4) & 3;
			if (!(afc & 1))
				continue;
			int cc = ts[3] & 0x0f;
			if (m_cc >= 0 && cc != ((m_cc + 1) & 0x0f) && cc != m_cc)
				m_inPes = false;
			m_cc = cc;
			size_t off = 4;
			if (afc & 2)
				off += 1 + ts[4];
			if (off >= TS_SIZE)
				continue;
			const uint8_t *p = ts + off;
			size_t len = TS_SIZE - off;
			if (ts[1] & 0x40) {
				/* the header is complete in the first packet in practice;
				 * still scrambled it has no start code (the TSC bits stay
				 * set after hardware descrambling) */
				m_inPes = onPes(p, len);
			} else if (m_inPes) {
				onPayload(p, len, m_pesPts);
				m_pesPts = NOPTS;
			}
		}
	}
};

static void onSignal(int)
{
	g_quit = 1;
}

int main(int argc, char **argv)
{
	const char *path = "/dev/dvb/adapter0/dreamaudio0";
	for (int i = 1; i < argc; i++) {
		if (!strcmp(argv[i], "-d"))
			g_debug = true;
		else
			path = argv[i];
	}
	openlog("dreamaudiod", LOG_PID, LOG_DAEMON);
	signal(SIGTERM, onSignal);
	signal(SIGINT, onSignal);
	av_log_set_level(g_debug ? AV_LOG_WARNING : AV_LOG_QUIET);

	int fd = open(path, O_RDWR | O_NONBLOCK | O_CLOEXEC);
	if (fd < 0) {
		LOG("open %s: %m", path);
		return 1;
	}
	if (ioctl(fd, DREAMAUDIO_SERVICE) < 0) {
		LOG("%s: another service runs: %m", path);
		return 1;
	}
	LOG("serving %s", path);

	Output out;
	Stream stream(out);
	dreamaudio_state state = {};
	bool playing = false;
	uint32_t clearSeq = 0;
	/* small reads: a pause stops the output within a fraction of a second,
	 * a large chunk (seconds of audio) would play on while the clock is held */
	std::vector<uint8_t> buf(TS_SIZE * 32);
	/* a restarted service joins a stream that already plays */
	bool changed = true;

	while (!g_quit) {
		/* in pause the data stays in the buffer */
		struct pollfd pfd = {fd, (short)(state.play_state == AUDIO_PAUSED ? POLLPRI : POLLIN | POLLPRI), 0};
		if (!changed && poll(&pfd, 1, 1000) < 0) {
			if (errno == EINTR)
				continue;
			break;
		}
		if (changed || (pfd.revents & POLLPRI)) {
			changed = false;
			dreamaudio_state st;
			if (ioctl(fd, DREAMAUDIO_GET_STATE, &st) == 0) {
				DBG("state seq %u play %u bypass %d channel %u clear %u",
				    st.seq, st.play_state, st.bypass, st.channel, st.clear_seq);
				if (!state.seq)
					clearSeq = st.clear_seq;
				stream.setBypass(st.bypass);
				stream.setChannel(st.channel);
				if (st.clear_seq != clearSeq) {
					clearSeq = st.clear_seq;
					stream.reset();
				}
				if (st.play_state == AUDIO_STOPPED && playing) {
					stream.reset();
					/* media playback needs the PCM */
					out.close();
				} else if (st.play_state == AUDIO_PAUSED && state.play_state == AUDIO_PLAYING) {
					out.flush();
				} else if (st.play_state == AUDIO_PLAYING && state.play_state == AUDIO_PAUSED) {
					stream.resume();
				}
				playing = st.play_state == AUDIO_PLAYING;
				/* the mosaic runs the system clock by the window of the
				 * audio; in PCR master mode tsync would read the demux PCR */
				bool mosaic = readFile("/proc/stb/video/decodermode").compare(0, 6, "mosaic") == 0;
				if (mosaic)
					writeFile("/sys/class/tsync/mode", "0");
				out.setSysclock(st.source == AUDIO_SOURCE_MEMORY || mosaic);
				out.setFree(st.source == AUDIO_SOURCE_MEMORY && !st.av_sync);
				state = st;
			}
		}
		/* in pause the data stays in the buffer */
		if ((pfd.revents & POLLIN) && state.play_state != AUDIO_PAUSED) {
			ssize_t n = read(fd, buf.data(), buf.size());
			if (n > 0 && playing)
				stream.feed(buf.data(), n);
		}
	}
	LOG("stopped");
	close(fd);
	return 0;
}
