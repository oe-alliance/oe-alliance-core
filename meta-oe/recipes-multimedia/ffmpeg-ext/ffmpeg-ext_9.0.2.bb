SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
DESCRIPTION = "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, \
               mux, demux, stream, filter and play pretty much anything that humans and machines \
               have created. It supports the most obscure ancient formats up to the cutting edge."
HOMEPAGE = "https://www.ffmpeg.org/"
SECTION = "libs"

# The FFmpeg of exteplayer3 only, in its own directory (${libdir}/ffmpeg-ext),
# next to the system FFmpeg 9 of the image, which it does not touch.

LICENSE = "BSD-2-Clause AND BSD-3-Clause AND GPL-2.0-or-later AND IJG AND ISC AND LGPL-2.1-or-later AND MIT"
LICENSE:${PN} = "GPL-2.0-or-later"
LICENSE:${PN}-libs = "${@bb.utils.contains('PACKAGECONFIG', 'gpl', 'GPL-2.0-or-later', 'LGPL-2.1-or-later', d)}"
LICENSE_FLAGS = "commercial"

LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LGPLv2.1;md5=eed22b3456132611e3d4aa7a7ec64dac \
                    file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

# skyjet18/FFmpeg release/9.0-patched: FFmpeg n9.0.2 with the OE-Alliance
# patches (the set of ffmpeg_9.%.bbappend, ffmpeg_ac4 included) and the
# playback patches of exteplayer3 (HLS, DASH, CENC, AC-4, WebVTT ...)
SRC_URI = " \
    git://github.com/skyjet18/FFmpeg.git;protocol=https;branch=release/9.0-patched \
    file://0002-ffbuild-common.mak-ensure-target-directories-are-cre.patch \
    file://makerace.patch \
"
SRCREV = "899f2ddd6be63ab4bcf4392faa721b7790862e73"

PV = "9.0.2+git"

# OpenBH and OpenViX build their own exteplayer3 trees (SRC_URI overrides in
# exteplayer3.bb); their master branches do not build against FFmpeg 9 yet
# (AVCodec.sample_fmts), so they keep ffmpeg-ext 6.1 until they do.
DEFAULT_PREFERENCE:openbh = "-1"
DEFAULT_PREFERENCE:openvix = "-1"

# Build fails when thumb is enabled: https://bugzilla.yoctoproject.org/show_bug.cgi?id=7717
ARM_INSTRUCTION_SET:armv4 = "arm"
ARM_INSTRUCTION_SET:armv5 = "arm"
ARM_INSTRUCTION_SET:armv6 = "arm"

DEPENDS = "nasm-native"

inherit autotools pkgconfig

PACKAGECONFIG ??= "avdevice avfilter avcodec avformat swresample swscale \
                   alsa bzlib lzma theora zlib \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xv xcb', '', d)}"

# libraries to build in addition to avutil
PACKAGECONFIG[avdevice] = "--enable-avdevice,--disable-avdevice"
PACKAGECONFIG[avfilter] = "--enable-avfilter,--disable-avfilter"
PACKAGECONFIG[avcodec] = "--enable-avcodec,--disable-avcodec"
PACKAGECONFIG[avformat] = "--enable-avformat,--disable-avformat"
PACKAGECONFIG[swresample] = "--enable-swresample,--disable-swresample"
PACKAGECONFIG[swscale] = "--enable-swscale,--disable-swscale"

# features to support
PACKAGECONFIG[alsa] = "--enable-alsa,--disable-alsa,alsa-lib"
PACKAGECONFIG[altivec] = "--enable-altivec,--disable-altivec,"
PACKAGECONFIG[bzlib] = "--enable-bzlib,--disable-bzlib,bzip2"
PACKAGECONFIG[fdk-aac] = "--enable-libfdk-aac --enable-nonfree,--disable-libfdk-aac,fdk-aac"
PACKAGECONFIG[gpl] = "--enable-gpl,--disable-gpl"
PACKAGECONFIG[gsm] = "--enable-libgsm,--disable-libgsm,libgsm"
PACKAGECONFIG[jack] = "--enable-indev=jack,--disable-indev=jack,jack"
PACKAGECONFIG[libopus] = "--enable-libopus,--disable-libopus,libopus"
PACKAGECONFIG[libvorbis] = "--enable-libvorbis,--disable-libvorbis,libvorbis"
PACKAGECONFIG[lzma] = "--enable-lzma,--disable-lzma,xz"
PACKAGECONFIG[mfx] = "--enable-libmfx,--disable-libmfx,intel-mediasdk"
PACKAGECONFIG[mp3lame] = "--enable-libmp3lame,--disable-libmp3lame,lame"
PACKAGECONFIG[openssl] = "--enable-openssl,--disable-openssl,openssl"
PACKAGECONFIG[sdl2] = "--enable-sdl2,--disable-sdl2,virtual/libsdl2"
PACKAGECONFIG[speex] = "--enable-libspeex,--disable-libspeex,speex"
PACKAGECONFIG[srt] = "--enable-libsrt,--disable-libsrt,srt"
PACKAGECONFIG[theora] = "--enable-libtheora,--disable-libtheora,libtheora libogg"
PACKAGECONFIG[v4l2] = "--enable-libv4l2,--disable-libv4l2,v4l-utils"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[vpx] = "--enable-libvpx,--disable-libvpx,libvpx"
PACKAGECONFIG[x264] = "--enable-libx264,--disable-libx264,x264"
PACKAGECONFIG[x265] = "--enable-libx265,--disable-libx265,x265"
PACKAGECONFIG[xcb] = "--enable-libxcb,--disable-libxcb,libxcb"
PACKAGECONFIG[xv] = "--enable-outdev=xv,--disable-outdev=xv,libxv"
PACKAGECONFIG[zlib] = "--enable-zlib,--disable-zlib,zlib"

# Check codecs that require --enable-nonfree
USE_NONFREE = "${@bb.utils.contains_any('PACKAGECONFIG', [ 'openssl' ], 'yes', '', d)}"

def cpu(d):
    for arg in (d.getVar('TUNE_CCARGS') or '').split():
        if arg.startswith('-mcpu='):
            return arg[6:]
    return 'generic'

# --enable-rpath: the libraries find each other in ${libdir}/${PN} (RUNPATH,
# which is not inherited), never the system FFmpeg of the same soname
EXTRA_OECONF = " \
    --disable-stripping \
    --enable-pic \
    --enable-shared \
    --disable-static \
    --disable-doc \
    --disable-programs \
    --enable-pthreads \
    --enable-rpath \
    ${@bb.utils.contains('USE_NONFREE', 'yes', '--enable-nonfree', '', d)} \
    \
    --cross-prefix=${TARGET_PREFIX} \
    \
    --ld='${CCLD}' \
    --cc='${CC}' \
    --cxx='${CXX}' \
    --arch=${TARGET_ARCH} \
    --target-os='linux' \
    --enable-cross-compile \
    --extra-cflags='${CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}' \
    --extra-ldflags='${LDFLAGS}' \
    --sysroot='${STAGING_DIR_TARGET}' \
    ${EXTRA_FFCONF} \
    --libdir=${libdir}/${PN} \
    --shlibdir=${libdir}/${PN} \
    --incdir=${includedir}/${PN} \
    --datadir=${datadir}/${PN} \
    ${@bb.utils.contains('AVAILTUNES', 'mips32r2', '', '--disable-mipsdsp --disable-mipsdspr2', d)} \
    --cpu=${@cpu(d)} \
    --pkg-config=pkg-config \
"

EXTRA_OECONF:append:linux-gnux32 = " --disable-asm"
# --enable-pic is used and x86 assembly is not PIC on x86
EXTRA_OECONF:append:x86 = " --disable-asm"

EXTRA_OECONF += "${@bb.utils.contains('TUNE_FEATURES', 'mipsisa64r6', '--disable-mips64r2 --disable-mips32r2', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('TUNE_FEATURES', 'mipsisa64r2', '--disable-mips64r6 --disable-mips32r6', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('TUNE_FEATURES', 'mips32r2', '--disable-mips64r6 --disable-mips32r6', '', d)}"
EXTRA_OECONF += "${@bb.utils.contains('TUNE_FEATURES', 'mips32r6', '--disable-mips64r2 --disable-mips32r2', '', d)}"
EXTRA_OECONF:append:mips = " --extra-libs=-latomic --disable-mips32r5 --disable-mipsdsp --disable-mipsdspr2 \
                             --disable-loongson2 --disable-loongson3 --disable-mmi --disable-msa"
EXTRA_OECONF:append:riscv32 = " --extra-libs=-latomic --disable-rvv --disable-asm"
EXTRA_OECONF:append:armv5 = " --extra-libs=-latomic"
EXTRA_OECONF:append:powerpc = " --extra-libs=-latomic"
EXTRA_OECONF:append:armv7a = "${@bb.utils.contains('TUNE_FEATURES','neon','',' --disable-neon',d)}"
EXTRA_OECONF:append:armv7ve = "${@bb.utils.contains('TUNE_FEATURES','neon','',' --disable-neon',d)}"

LDFLAGS:append:x86 = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', ' -fuse-ld=bfd ', '', d)}"

EXTRA_OEMAKE = "V=1"

do_configure() {
    export TMPDIR="${B}/tmp"
    mkdir -p ${B}/tmp
    ${S}/configure ${EXTRA_OECONF}
    sed -i -e "s,^X86ASMFLAGS=.*,& --debug-prefix-map=${S}=${TARGET_DBGSRC_DIR} --debug-prefix-map=${B}=${TARGET_DBGSRC_DIR},g" ${B}/ffbuild/config.mak
}

# patch out build host paths for reproducibility
do_compile:prepend:class-target() {
        sed -i -e "s,${WORKDIR},,g" ${B}/config.h
}

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg ${PN}-libs"

FILES:${PN}-libs = "${libdir}/${PN}/*.so.*"

FILES:${PN}-dev += " \
    ${libdir}/${PN}/*.so \
    ${libdir}/${PN}/pkgconfig/* \
    ${datadir}/${PN}/examples/* \
    ${includedir}/${PN} \
"

FILES:${PN} = ""

# The libraries have the sonames of the system FFmpeg 9 (libavformat.so.63
# ...). Not registered as shlib providers: otherwise every package that
# links the system FFmpeg would find two providers ("Multiple shlib
# providers"), and the dependencies among these libraries would point to
# the system packages. exteplayer3 depends on ${PN}-libs by name.
PRIVATE_LIBS = "libavcodec.so.* libavdevice.so.* libavfilter.so.* libavformat.so.* \
                libavutil.so.* libswresample.so.* libswscale.so.*"

# RUNPATH ${libdir}/${PN} (--enable-rpath)
INSANE_SKIP:${PN}-libs += "rpaths useless-rpaths"
