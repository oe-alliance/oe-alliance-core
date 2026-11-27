// SPDX-License-Identifier: MIT
#include <errno.h>
#include <fcntl.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <libavformat/avformat.h>
#include <amvdec_ioc.h>

static int set_value(int fd, uint32_t cmd, uint32_t value)
{
	struct am_ioctl_parm arg = { .cmd = cmd, .data_32 = value };
	return ioctl(fd, AMSTREAM_IOC_SET, &arg);
}

static int display(const AVCodecParameters *codec, const AVPacket *packet)
{
	int avc = codec->codec_id == AV_CODEC_ID_H264;
	struct dec_sysinfo info = {
		.format = avc ? 4 : 0, .width = codec->width, .height = codec->height, .rate = 3840
	};
	int fd = open("/dev/amstream_vframe", O_WRONLY | O_NONBLOCK | O_CLOEXEC);
	if (fd < 0)
		return -1;
	int result = -1;
	if (set_value(fd, 0x105, avc ? VFORMAT_H264 : VFORMAT_MPEG12) < 0 ||
	    ioctl(fd, AMSTREAM_IOC_SYSINFO, &info) < 0 ||
	    set_value(fd, 0x174, FRAME_BASE_PATH_TUNNEL_MODE) < 0 || set_value(fd, 0x111, 0) < 0)
		goto done;

	int policy = open("/sys/class/video/blackout_policy", O_WRONLY | O_CLOEXEC);
	if (policy < 0)
		goto done;
	ssize_t kept = write(policy, "0", 1);
	close(policy);
	if (kept != 1)
		goto done;
	// Prime the decoder and allow the display sink to retain the last I-frame.
	for (int n = 0; n < 4; ++n) {
		if (set_value(fd, 0x10e, 90000 + 3600 * n) < 0)
			goto done;
		ssize_t sent;
		for (int retry = 0; ; ++retry) {
			sent = write(fd, packet->data, packet->size);
			if (sent >= 0 || retry >= 50 || (errno != EAGAIN && errno != EINTR))
				break;
			usleep(10000);
		}
		if (sent != packet->size) {
			if (sent >= 0)
				errno = EIO;
			goto done;
		}
		usleep(40000);
	}
	usleep(200000);
	result = 0;
done:
	if (result)
		perror("showiframe: Meson decoder");
	close(fd);
	return result;
}

int main(int argc, char **argv)
{
	if (argc != 2) {
		fprintf(stderr, "Usage: showiframe FILE.mvi\n");
		return 2;
	}
	unsigned char header[4];
	FILE *file = fopen(argv[1], "rb");
	if (!file) {
		perror(argv[1]);
		return 1;
	}
	size_t count = fread(header, 1, sizeof(header), file);
	fclose(file);
	if (count != sizeof(header))
		return 1;
	// Receiver .mvi files are elementary streams, not FFmpeg's Motion Pixels container.
	const char *format = memcmp(header, "\x00\x00\x01\xb3", 4) == 0 ? "mpegvideo" : "h264";
	AVFormatContext *input = NULL;
	AVPacket *packet = av_packet_alloc();
	int result = 1;
	if (!packet || avformat_open_input(&input, argv[1], av_find_input_format(format), NULL) < 0 ||
	    avformat_find_stream_info(input, NULL) < 0)
		goto done;
	int stream = av_find_best_stream(input, AVMEDIA_TYPE_VIDEO, -1, -1, NULL, 0);
	if (stream < 0)
		goto done;
	while (av_read_frame(input, packet) >= 0) {
		if (packet->stream_index == stream && (packet->flags & AV_PKT_FLAG_KEY)) {
			result = display(input->streams[stream]->codecpar, packet) < 0;
			break;
		}
		av_packet_unref(packet);
	}
done:
	if (result)
		fprintf(stderr, "showiframe: could not display %s\n", argv[1]);
	av_packet_free(&packet);
	avformat_close_input(&input);
	return result;
}
