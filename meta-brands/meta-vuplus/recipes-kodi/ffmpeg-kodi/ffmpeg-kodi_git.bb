DESCRIPTION = "FFMPEG for Kodi"

DEPENDS += " zlib "

LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=4e0e4c9534db149e6b733ea75e421da7"

PR = "r1"

inherit autotools pkgconfig

SRC_URI = "https://github.com/xbmc/FFmpeg/archive/2.6.4-Isengard.tar.gz"

SRC_URI[md5sum] = "3dbd015fbfea2cbedf1fbd0779ab987e"
SRC_URI[sha256sum] = "2487a6d4ad5701ad22582fc064ce39b60c383eec4958ca1e3218379035fa523f"

S = "${WORKDIR}/FFmpeg-2.6.4-Isengard/"

EXTRA_OECONF = " \
        --extra-cflags='-fdata-sections -ffunction-sections' \
        --extra-ldflags='-Wl,--gc-sections' \
        --prefix=${prefix} \
        --incdir=${prefix}/include/ffmpeg \
        --build-suffix=-ffmpeg \
        --arch=${TARGET_ARCH} \
        --cross-prefix=${TARGET_PREFIX} \
        --target-os=linux \
        --sysroot=${STAGING_DIR_HOST} \
        --enable-bzlib \
        --enable-cross-compile \
        --enable-openssl \
        --enable-nonfree \
        --enable-pthreads \
        --disable-pic \
        --disable-shared \
        --enable-swscale \
        --enable-zlib \
        --enable-gpl \
        --enable-static \
        --disable-devices \
        --disable-htmlpages \
        --disable-manpages \
        --disable-mipsfpu \
        --disable-mipsdspr1 \
        --disable-mipsdspr2 \
        --disable-podpages \
        --disable-stripping \
        --disable-txtpages \
        --disable-vdpau \
"

EXTRA_OECONF_append_vusolo4k = " \
        --extra-cflags=' -march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a15' \
"

do_configure() {
    cd ${S}
        ./configure ${EXTRA_OECONF}
}

do_compile() {
   cd ${S}
   make -j 2 BUILDDIR=${BUILDDIR} DESTDIR=${D}
}

do_install() {
   cd ${S}
   make -j 2 BUILDDIR=${BUILDDIR} DESTDIR=${D} install
}

FILES_${PN} += "/"
