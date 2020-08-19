FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "libxml2"

PACKAGECONFIG_append = " libbluray libfreetype librtmp openssl"

PACKAGECONFIG[libbluray] = "--enable-libbluray --enable-protocol=bluray,--disable-libbluray,libbluray"
PACKAGECONFIG[libfreetype] = "--enable-libfreetype,--disable-libfreetype,freetype"
PACKAGECONFIG[librtmp] = "--enable-librtmp,--disable-librtmp,librtmp rtmpdump"
PACKAGECONFIG[libv4l2] = "--enable-libv4l2,--disable-libv4l2,v4l-utils"

MIPSFPU = "${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-mipsfpu', '--enable-mipsfpu', d)}"

SRC_URI_append = " \
        file://4_02_fix_mpegts.patch \
        file://4_03_allow_to_choose_rtmp_impl_at_runtime.patch \
        file://4_04_hls_replace_key_uri.patch \
        file://4_06_optimize_aac.patch \
        file://4_07_increase_buffer_size.patch \
        file://4_08_recheck_discard_flags.patch \
        file://4_09_ffmpeg_fix_edit_list_parsing.patch \
        file://4_11_rtsp.patch \
        file://4_12_dxva2.patch \
        "

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
    \
    --disable-muxers \
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
    --disable-encoders \
    --enable-encoder=mpeg1video \
    --enable-encoder=png \
    --enable-encoder=libx264 \
    --enable-encoder=ljpeg \
    --enable-encoder=mpeg4 \
    --enable-encoder=jpeg2000 \
    --enable-encoder=jpegls \
    --enable-encoder=rawvideo \
    \
    --disable-debug \
    --disable-doc \
    --disable-htmlpages \
    --disable-manpages \
    --disable-podpages \
    --disable-txtpages \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "${MIPSFPU} --disable-vfp --disable-neon --disable-mipsdsp --disable-mipsdspr2", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "--enable-armv6 --enable-armv6t2 --enable-vfp --enable-neon", "", d)} \
    ${@bb.utils.contains("TUNE_FEATURES", "aarch64", "--enable-armv8 --enable-vfp --enable-neon", "", d)} \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS} -ffunction-sections -fdata-sections -fno-aggressive-loop-optimizations" \
    --extra-ldflags="${TARGET_LDFLAGS},--gc-sections -Wl,--print-gc-sections,-lrt" \
"
