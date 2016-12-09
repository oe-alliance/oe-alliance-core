DESCRIPTION="Gmediarender DLNA Renderer"
MAINTAINER = "TitanNit Developer"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "auto+gitr${SRCPV}"
PR = "r1"
PR = "r2"

SRC_URI="git://github.com/FFmpeg/FFmpeg.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

FFMPEG_CONFIGURE  = "--disable-static --enable-shared --enable-small --disable-runtime-cpudetect"
FFMPEG_CONFIGURE += "--disable-ffserver --disable-ffplay --disable-ffprobe"
FFMPEG_CONFIGURE += "--disable-doc --disable-htmlpages --disable-manpages --disable-podpages --disable-txtpages"
FFMPEG_CONFIGURE += "--disable-asm --disable-altivec --disable-amd3dnow --disable-amd3dnowext --disable-mmx --disable-mmxext"
FFMPEG_CONFIGURE += "--disable-sse --disable-sse2 --disable-sse3 --disable-ssse3 --disable-sse4 --disable-sse42 --disable-avx --disable-fma4"
#FFMPEG_CONFIGURE += "--disable-armv5te --disable-armv6 --disable-armv6t2 --disable-vfp --disable-neon --disable-vis --disable-inline-asm"
FFMPEG_CONFIGURE += "--disable-armv5te --disable-armv6 --disable-armv6t2 --disable-vfp --disable-neon --disable-inline-asm"

FFMPEG_CONFIGURE += "--disable-yasm --disable-mips32r2 --disable-mipsdspr1 --disable-mipsdspr2 --disable-mipsfpu --disable-fast-unaligned"
FFMPEG_CONFIGURE += "--disable-muxers"
FFMPEG_CONFIGURE += "--enable-muxer=flac --enable-muxer=mp3 --enable-muxer=h261 --enable-muxer=h263 --enable-muxer=h264"
FFMPEG_CONFIGURE += "--enable-muxer=image2 --enable-muxer=mpeg1video --enable-muxer=mpeg2video --enable-muxer=ogg"
FFMPEG_CONFIGURE += "--disable-encoders"
FFMPEG_CONFIGURE += "--enable-encoder=aac --enable-encoder=h261 --enable-encoder=h263 --enable-encoder=h263p --enable-encoder=ljpeg"
FFMPEG_CONFIGURE += "--enable-encoder=mjpeg --enable-encoder=mpeg1video --enable-encoder=mpeg2video --enable-encoder=png"
FFMPEG_CONFIGURE += "--disable-decoders"
FFMPEG_CONFIGURE += "--enable-decoder=aac --enable-decoder=dvbsub --enable-decoder=dvdsub --enable-decoder=flac --enable-decoder=h261 --enable-decoder=h263"
FFMPEG_CONFIGURE += "--enable-decoder=h263i --enable-decoder=h264 --enable-decoder=iff_byterun1 --enable-decoder=mjpeg"
FFMPEG_CONFIGURE += "--enable-decoder=mp3 --enable-decoder=mpeg1video --enable-decoder=mpeg2video --enable-decoder=png"
FFMPEG_CONFIGURE += "--enable-decoder=theora --enable-decoder=vorbis --enable-decoder=wmv3 --enable-decoder=pcm_s16le"
FFMPEG_CONFIGURE += "--enable-decoder=rawvideo --enable-decoder=wmapro --enable-decoder=wmav1 --enable-decoder=wmav2 --enable-decoder=wmavoice"
FFMPEG_CONFIGURE += "--enable-decoder=iff_byterun1 --enable-decoder=ra_144 --enable-decoder=ra_288"
FFMPEG_CONFIGURE += "--enable-demuxer=mjpeg --enable-demuxer=wav --enable-demuxer=rtsp"
FFMPEG_CONFIGURE += "--enable-parser=mjpeg"
FFMPEG_CONFIGURE += "--disable-indevs --disable-outdevs --disable-bsfs --disable-debug"
FFMPEG_CONFIGURE += "--enable-pthreads --enable-bzlib --enable-zlib --enable-librtmp --enable-stripping"

# configuration settings
#ffmpg_config = "--prefix=$(PREFIX) --extra-version="xbmc-$(VERSION)"
#ffmpg_config += "--cc=$(CC) --cxx=$(CXX)"
ffmpg_config = "--disable-devices --disable-doc"
ffmpg_config += "--disable-ffplay --disable-ffmpeg"
ffmpg_config += "--disable-ffprobe --disable-ffserver"
ffmpg_config += "--enable-gpl --enable-runtime-cpudetect"
ffmpg_config += "--enable-postproc --enable-pthreads"
ffmpg_config += "--enable-muxer=spdif --enable-muxer=adts"
ffmpg_config += "--enable-muxer=asf --enable-muxer=ipod"
ffmpg_config += "--enable-encoder=ac3 --enable-encoder=aac"
ffmpg_config += "--enable-encoder=wmav2 --enable-protocol=http"
#  ffmpg_config += "--arch=$(CPU) --enable-cross-compile"
#  ffmpg_config += "--target-os=$(OS) --cpu=$(CPU)"
ffmpg_config += "--enable-vdpau --enable-vaapi --enable-gnutls"
ffmpg_config += "--enable-libvorbis --enable-muxer=ogg --enable-encoder=libvorbis"
ffmpg_config += "--disable-mips32r2 --disable-mipsdspr1 --disable-mipsdspr2"
#  ffmpg_config += "--disable-debug"


EXTRA_OECONF = " \
        \
        --cross-prefix=${TARGET_PREFIX} \
        --prefix=${prefix}/ \
        \
        --extra-cflags="--sysroot=${STAGING_DIR_TARGET}" \
        --extra-ldflags="--sysroot=${STAGING_DIR_TARGET}" \
        --enable-hardcoded-tables \
		--pkg-config="pkg-config" \
		--target-os=linux \
		--arch=${TARGET_ARCH} \
		--enable-postproc \
        ${EXTRA_FFCONF} \
"

do_configure() {
	cd ${S}
#	${S}/configure ${EXTRA_OECONF} ${FFMPEG_CONFIGURE}
	${S}/configure ${EXTRA_OECONF} ${ffmpg_config}
}

do_compile() {
	cd ${S}
	make -j 8
}

autotools_do_install() {
	cd ${S}
	make install DESTDIR=${WORKDIR}/image
	install -m 0755 ${S}/libavformat/internal.h ${WORKDIR}/image/usr/include/libavformat
}

do_install_append() {
	install -m 0755 ${S}/libavformat/internal.h ${D}/usr/include/libavformat
}