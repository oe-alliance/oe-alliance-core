FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/skyjet18:"

PR = "r0"

S = "${UNPACKDIR}/ffmpeg-${PV}"

SRC_URI += "file://0002-fix-mpegts.patch \
            file://0003-allow-to-choose-rtmp-impl-at-runtime.patch \
            file://0005-mips64-cpu-detection.patch \
            file://0006-optimize-aac.patch \
            file://0007-increase-buffer-size.patch \
            file://0008-recheck-discard-flags.patch \
            file://0009-ffmpeg-fix-edit-list-parsing.patch \
            file://0011-rtsp.patch \
            file://0012-dxva2.patch \
            file://0013-add-av_stream_get_first_dts-for-chromium.patch \
            file://ffmpeg_ac4.patch \
            file://ffmpeg-armv5te-binutils-2.4.patch \
            \
            file://0001-avformat-mov-add-support-for-multiple-decryption-key.patch \
            file://0002-added-debug-logs-for-multiple-decryption-keys.patch \
            file://0003-added-support-for-parsing-cenc-key-s-from-MPD-playli.patch \
            file://0004-added-possibility-to-set-decryption-keys-directly-in.patch \
            file://0005-changed-default-user-agent.patch \
            file://0006-options-to-replace-parts-of-key-uri-in-HLS.patch \
            file://0007-fixed-parsing-webvtt-with-STYLE-and-REGION-block.patch \
            file://0008-FFmpeg-devel-1-2-avformat-hls-fix-to-seek-logic.patch \
            file://0009-FFmpeg-devel-2-2-avformat-mov-fix-to-detect-if-strea.patch \
            file://0010-FFmpeg-devel-v2-1-2-avformat-webvttdec-Add-support-f.patch \
            file://0011-FFmpeg-devel-v2-2-2-avformat-hls-Add-subtitle-suppor.patch \
            file://0012-fixed-segfault-in-hls-subtitle-handling.patch \
            file://0013-avformat-dashdec-don-t-reload-manifest-often-then-ne.patch \
            file://0014-avformat-dashdec-fixed-subtitles-segments-update.patch \
            file://0015-avformat-dashdec-don-t-read-new-segments-too-fast-to.patch \
            file://0016-avformat-hls-don-t-use-byterange-when-loading-subtit.patch \
            file://0017-dashdec-use-min-buffer-time-when-selecting-start-off.patch \
            file://0018-webvttdec-option-to-insert-fake-subtitle-at-the-end.patch \
            file://0019-dashdec-improved-handling-manifests-with-one-segment.patch \
            file://0020-subtitles-fixed-subtitle-search-after-seek.patch \
            file://0021-dashdec-fixed-proper-segment-number-calculation.patch \
            file://0022-dashdec-allow-setting-restart-in-reopen_demux_.patch \
            file://0023-dashdec-ensure-that-pts-always-starts-from-zero.patch \
            file://0024-dashdec-set-optimal-http-settings-based-on-media-typ.patch \
            file://0025-dashdec-don-t-skip-first-segment.patch \
            file://0026-dashdec-fix-integer-overflow-by-seeking.patch \
            file://0027-dashdec-added-startover-attribute-to-manifest.patch \
            file://0028-dashdec-check-borders-when-seeking-in-live-mode.patch \
            file://0029-hls-improved-handling-of-event-type-playlist.patch \
            file://0030-dashdec-fixed-playback-of-DASH-with-ampersand-in-Bas.patch \
            file://0031-add-X-DRM-Api-Level-header-when-using-with-archivczs.patch \
            file://0032-dashdec-fixed-splitted-subtitles.patch \
            file://0033-dashdec-fixed-playback-of-some-live-streams.patch \
            file://0001-decryption-key-extention.patch \
            "

PACKAGECONFIG:append = " gpl libbluray dav1d libfreetype librtmp libxml2 openssl x264"

PACKAGECONFIG[libbluray] = "--enable-libbluray --enable-protocol=bluray,--disable-libbluray,libbluray"
PACKAGECONFIG[dav1d] = "--enable-libdav1d,--disable-libdav1d,dav1d"
PACKAGECONFIG[libfreetype] = "--enable-libfreetype,--disable-libfreetype,freetype"
PACKAGECONFIG[librtmp] = "--enable-librtmp,--disable-librtmp,librtmp rtmpdump"
PACKAGECONFIG[libv4l2] = "--enable-libv4l2,--disable-libv4l2,v4l-utils"
PACKAGECONFIG[libxml2] = "--enable-libxml2,--disable-libxml2,libxml2"

MIPSFPU = "${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)}"

EXTRA_FFCONF = " \
    --prefix=${prefix} \
    --disable-runtime-cpudetect \
    --disable-altivec \
    --disable-amd3dnow \
    --disable-amd3dnowext \
    --disable-mmx \
    --disable-mmxext \
    --disable-sse \
    --disable-sse2 \
    --disable-sse3 \
    --disable-ssse3 \
    --disable-sse4 \
    --disable-sse42 \
    --disable-avx \
    --disable-xop \
    --disable-fma3 \
    --disable-fma4 \
    --disable-avx2 \
    --disable-inline-asm \
    --disable-yasm \
    --disable-x86asm \
    --disable-fast-unaligned \
    --enable-protocol=http \
    --enable-demuxer=dash \
    \
    --disable-muxers \
    --enable-muxer=adts \
    --enable-muxer=mpeg1video \
    --enable-muxer=h264 \
    --enable-muxer=mp4 \
    --enable-muxer=image2 \
    --enable-muxer=mjpeg \
    --enable-muxer=rawvideo \
    --enable-muxer=mpeg2video \
    --enable-muxer=matroska \
    --enable-muxer=m4v \
    --enable-muxer=image2pipe \
    --enable-muxer=apng \
    --enable-muxer=mpegts \
    --enable-muxer=asf \
    --enable-muxer=spdif \
    --disable-encoders \
    --enable-encoder=ac3 \
    --enable-encoder=aac \
    --enable-encoder=mpeg1video \
    --enable-encoder=libx264 \
    --enable-encoder=ljpeg \
    --enable-encoder=mjpeg \
    --enable-encoder=mpeg4 \
    --enable-encoder=jpeg2000 \
    --enable-encoder=jpegls \
    --enable-encoder=png \
    --enable-encoder=rawvideo \
    --enable-encoder=wmav2 \
    --enable-decoder=truehd \
    --enable-decoder=mlp \
    \
    --disable-debug \
    --disable-doc \
    --disable-htmlpages \
    --disable-manpages \
    --disable-podpages \
    --disable-txtpages \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "${MIPSFPU} --extra-libs=-latomic --disable-mips32r5 --disable-mipsdsp --disable-mipsdspr2 \
                             --disable-loongson2 --disable-loongson3 --disable-mmi --disable-msa", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "--enable-armv6 --enable-armv6t2 --enable-vfp --enable-neon", "", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "aarch64", "--enable-armv8 --enable-vfp --enable-neon", "", d)} \
"
