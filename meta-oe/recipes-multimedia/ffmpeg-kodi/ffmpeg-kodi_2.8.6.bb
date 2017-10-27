DESCRIPTION = "FFMPEG for Kodi"

DEPENDS += " zlib openssl"

LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=5c6d1ed56d15ca87ddec48d0c3a2051d"

PV = "2.8.6"

inherit autotools pkgconfig

SRC_URI = "https://github.com/xbmc/FFmpeg/archive/2.8.6-Jarvis-16.0.tar.gz"

SRC_URI[md5sum] = "91292b7dc99b9d055e4ad7b9644c9738"
SRC_URI[sha256sum] = "b08d8dc171f19627025136b498dfcad4f1b84923de7a62696d8a9a7f1d979b01"

S = "${WORKDIR}/FFmpeg-2.8.6-Jarvis-16.0/"

EXTRA_OECONF = " \
        --extra-cflags='-fdata-sections -ffunction-sections' \
        --extra-ldflags='-Wl,--gc-sections' \
        --prefix=${prefix} \
        --build-suffix=-${PN} \
        --incdir=${prefix}/include/${PN} \
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

EXTRA_OECONF_append_vuuno4k = " \
        --extra-cflags=' -march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a15' \
"

EXTRA_OECONF_append_vuultimo4k = " \
        --extra-cflags=' -march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a15' \
"

EXTRA_OECONF_append_wetekplay = " \
	--extra-cflags=' -march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a9' \
	--enable-muxer=spdif \
	--enable-muxer=adts \
	--enable-muxer=asf \
	--enable-muxer=ipod \
	--enable-encoder=ac3 \
	--enable-encoder=aac \
	--enable-encoder=wmav2 \
	--enable-avcodec \
	--enable-postproc \
	--enable-gpl \
	--enable-protocol=http \
	--enable-runtime-cpudetect \
	--enable-optimizations \
	--disable-debug \
	--disable-muxers \
	--disable-encoders \
	--disable-decoder=mpeg_xvmc \
	--disable-devices \
	--disable-ffprobe \
	--disable-ffplay \
	--disable-ffserver \
	--disable-ffmpeg \
	--disable-vaapi \
	--disable-crystalhd \
	--disable-openssl \
	--disable-hardcoded-tables \
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
