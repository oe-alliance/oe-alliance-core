SUMMARY = "libeplayer3 mediaplayer lib and console"
DESCRIPTION = "Core of movie player for Titan the libeplayer using the ffmpeg solution"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"

LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit pkgconfig

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/titan;module=libeplayer3;protocol=http"

DEPENDS = "ffmpeg libbluray"
RDEPENDS:${PN} = "ffmpeg libbluray"

inherit gitpkgv upx-compress


SSTATE_ALLOW_OVERLAP_FILES += "${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0"

S = "${WORKDIR}/libeplayer3"

CFLAGS:append = " -Wall -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -DHAVE_FLV2MPEG4_CONVERTER"

CFLAGS:append = " \
	-I${S}/include \
	-I${S}/external \
    -I${S}/external/flv2mpeg4 \
	"
	
CFLAGS:append:arm = " -DARM -DMIPSEL"
CFLAGS:append:mipsel = " -DMIPSEL"
CFLAGS:append:sh4 = " -DSH4"

LDFLAGS:prepend = " -lswscale -ldl -lpthread -lavformat -lavcodec -lavutil -lswresample "

SOURCE_FILES:BIN = "main/exteplayer.c"

SOURCE_FILES:LIB = "container/container.c"
SOURCE_FILES:LIB =+ "container/container_ffmpeg.c"
SOURCE_FILES:LIB =+ "manager/manager.c"
SOURCE_FILES:LIB =+ "manager/audio.c"
SOURCE_FILES:LIB =+ "manager/video.c"
SOURCE_FILES:LIB =+ "manager/subtitle.c"
SOURCE_FILES:LIB =+ "output/output_subtitle.c"
SOURCE_FILES:LIB =+ "output/output.c"
SOURCE_FILES:LIB =+ "output/writer/common/pes.c"
SOURCE_FILES:LIB =+ "output/writer/common/misc.c"
SOURCE_FILES:LIB =+ "output/writer/common/writer.c"
SOURCE_FILES:LIB =+ "output/linuxdvb_buffering.c"
SOURCE_FILES:LIB =+ "output/graphic_subtitle.c"
SOURCE_FILES:LIB =+ "playback/playback.c"
SOURCE_FILES:LIB =+ "external/ffmpeg/src/bitstream.c"
SOURCE_FILES:LIB =+ "external/ffmpeg/src/latmenc.c"
SOURCE_FILES:LIB =+ "external/ffmpeg/src/mpeg4audio.c"
SOURCE_FILES:LIB =+ "external/ffmpeg/src/xiph.c"
SOURCE_FILES:LIB =+ "external/flv2mpeg4/src/m4vencode.c"
SOURCE_FILES:LIB =+ "external/flv2mpeg4/src/flvdecoder.c"
SOURCE_FILES:LIB =+ "external/flv2mpeg4/src/dcprediction.c"
SOURCE_FILES:LIB =+ "external/flv2mpeg4/src/flv2mpeg4.c"
SOURCE_FILES:LIB =+ "external/plugins/src/png.c"

SOURCE_FILES:LIB =+ "${@bb.utils.contains("TARGET_ARCH", "sh4", "\
output/linuxdvb_sh4.c \
output/writer/sh4/writer.c \
output/writer/sh4/aac.c \
output/writer/sh4/ac3.c \
output/writer/sh4/divx2.c \
output/writer/sh4/dts.c \
output/writer/sh4/h263.c \
output/writer/sh4/h264.c \
output/writer/sh4/mp3.c \
output/writer/sh4/mpeg2.c \
output/writer/sh4/pcm.c \
output/writer/sh4/vc1.c \
output/writer/sh4/wma.c \
output/writer/sh4/wmv.c ", " \
output/linuxdvb_mipsel.c \
output/writer/mipsel/writer.c \
output/writer/mipsel/aac.c \
output/writer/mipsel/ac3.c \
output/writer/mipsel/bcma.c \
output/writer/mipsel/mp3.c \
output/writer/mipsel/pcm.c \
output/writer/mipsel/lpcm.c \
output/writer/mipsel/dts.c \
output/writer/mipsel/amr.c \
output/writer/mipsel/h265.c \
output/writer/mipsel/h264.c \
output/writer/mipsel/mjpeg.c \
output/writer/mipsel/mpeg2.c \
output/writer/mipsel/mpeg4.c \
output/writer/mipsel/divx3.c \
output/writer/mipsel/vp.c \
output/writer/mipsel/wmv.c \
output/writer/mipsel/vc1.c ", d)}"

do_compile() {
	cd ${WORKDIR}/libeplayer3
#	make clean
	if [ -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0 ]; then rm ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0; fi
	if [ -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0 ]; then rm ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0; fi
	if [ -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0 ]; then rm ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so; fi

	${CC} ${SOURCE_FILES:LIB} ${CFLAGS} -fPIC -shared -Wl,-soname,libeplayer3.so.0 -o libeplayer3.so.0.0.0 ${LDFLAGS}
#	${STRIP} libeplayer3.so.0.0.0
	if [ ! -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so ]; then cp -a libeplayer3.so.0.0.0 ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so; fi
	if [ ! -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0 ]; then cp -a libeplayer3.so.0.0.0 ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0; fi
	if [ ! -e ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0 ]; then cp -a libeplayer3.so.0.0.0 ${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0; fi

#	smal binary with linked lib
    ${CC} ${SOURCE_FILES:BIN} ${CFLAGS} -o eplayer3 -leplayer3 -lpthread

#	full binary
#	${CC} ${SOURCE_FILES:BIN} ${SOURCE_FILES:LIB} ${CFLAGS} -o eplayer3 ${LDFLAGS}
}

FILES:${PN} = "/usr/bin"
FILES:${PN} += "/usr/lib"

do_install:append() {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -m 0755 eplayer3 ${D}${bindir}
    install -m 0755 libeplayer3.so.0.0.0 ${D}${libdir}/
    ln -s libeplayer3.so.0.0.0 ${D}${libdir}/libeplayer3.so
    ln -s libeplayer3.so.0.0.0 ${D}${libdir}/libeplayer3.so.0
}

INSANE_SKIP:${PN} += "ldflags"

