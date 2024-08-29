FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += "file://0001-makefile-add-demux-header.patch \
            file://0002-fix-mpegts.patch \
            file://0003-allow-to-choose-rtmp-impl-at-runtime.patch \
            file://0004-hls-replace-key-uri.patch \
            file://0005-mips64-cpu-detection.patch \
            file://0006-optimize-aac.patch \
            file://0007-increase-buffer-size.patch \
            file://0008-recheck-discard-flags.patch \
            file://0009-ffmpeg-fix-edit-list-parsing.patch \
            file://0011-rtsp.patch \
            file://0012-dxva2.patch \
            file://0013-add-av_stream_get_first_dts-for-chromium.patch \
            file://ffmpeg_ac4.patch \
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
    --disable-static \
    --disable-runtime-cpudetect \
    --enable-ffprobe \
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

BBCLASSEXTEND = "native"
