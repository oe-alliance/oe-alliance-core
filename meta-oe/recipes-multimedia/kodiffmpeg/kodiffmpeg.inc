DESCRIPTION = "KODI FFMPEG external libraries"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=af4fe319be2972a4fa475030d294cf1f"

S = "${WORKDIR}/FFmpeg-${PV}-Helix/"

COMPATIBLE_MACHINE = "(wetekplay)"

inherit autotools-brokensep pkgconfig

DEPENDS = "libvorbis zlib"

PACKAGECONFIG ??= "pthreads zlib bzip2 libvorbis vfp"

PACKAGECONFIG[pthreads]   = "--enable-pthreads,--disable-pthreads"
PACKAGECONFIG[zlib]	  = "--enable-zlib,--disable-zlib,zlib"
PACKAGECONFIG[bzip2]	  = "--enable-bzlib,--disable-bzlib,bzip2"
PACKAGECONFIG[libvorbis]  = "--enable-libvorbis --enable-muxer=ogg --enable-encoder=libvorbis,--disable-libvorbis,libvorbis"
PACKAGECONFIG[gnutls]	  = "--enable-gnutls --extra-cflags=-fPIC,--disable-gnutls,gnutls"
PACKAGECONFIG[vfp]	  = "--enable-vfp,--disable-vfp"

#FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations  -ftree-vectorize -fomit-frame-pointer -O4 -ffast-math"
#BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_OECONF = " \
--prefix=${prefix} \
--build-suffix=-${PN} \
--incdir=${prefix}/include/${PN} \
--arch=${TARGET_ARCH} \
--cross-prefix=${TARGET_PREFIX} \
--disable-stripping \
--enable-cross-compile \
--enable-pthreads \
--enable-shared \
--disable-static \
--enable-swscale \
--enable-pic \
--target-os=linux \
--enable-gpl \
--sysroot=${STAGING_DIR_HOST} \
--disable-vdpau \
--extra-cflags=' -march=armv7-a -mfloat-abi=hard -mfpu=neon -mtune=cortex-a9' \
--extra-version=kodi-2.4.4-Helix \
--disable-debug \
--disable-muxers \
--disable-encoders \
--disable-decoder=mpeg_xvmc \
--disable-devices \
--disable-ffprobe \
--disable-ffplay \
--disable-ffserver \
--disable-ffmpeg \
--disable-vdpau \
--disable-vaapi \
--disable-crystalhd \
--disable-openssl \
--disable-hardcoded-tables \
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
"

do_configure() {
  ./configure ${EXTRA_OECONF}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
INSANE_SKIP_${PN} = "textrel"