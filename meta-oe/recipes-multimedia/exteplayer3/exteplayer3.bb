SUMMARY = "exteplayer3 - media player for E2"
DESCRIPTION = "Core of movie player for E2 based on the libeplayer using the ffmpeg solution"
SECTION = "multimedia"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "ffmpeg"
RDEPENDS_${PN} = "ffmpeg"

inherit gitpkgv

PV = "49+gitr${SRCPV}"
PKGV = "49+gitr${GITPKGV}"

PR = "r0"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/samsamsam-iptvplayer/exteplayer3.git;branch=master"

S = "${WORKDIR}/git/"

SOURCE_FILES = "main/exteplayer.c"
SOURCE_FILES =+ "container/container.c"
SOURCE_FILES =+ "container/container_ffmpeg.c"
SOURCE_FILES =+ "manager/manager.c"
SOURCE_FILES =+ "manager/audio.c"
SOURCE_FILES =+ "manager/video.c"
SOURCE_FILES =+ "manager/subtitle.c"
SOURCE_FILES =+ "output/output_subtitle.c"
SOURCE_FILES =+ "output/output.c"
SOURCE_FILES =+ "output/writer/common/pes.c"
SOURCE_FILES =+ "output/writer/common/misc.c"
SOURCE_FILES =+ "output/writer/common/writer.c"
SOURCE_FILES =+ "output/linuxdvb_buffering.c"
SOURCE_FILES =+ "playback/playback.c"
SOURCE_FILES =+ "external/ffmpeg/src/bitstream.c"
SOURCE_FILES =+ "external/ffmpeg/src/latmenc.c"
SOURCE_FILES =+ "external/ffmpeg/src/mpeg4audio.c"
SOURCE_FILES =+ "external/flv2mpeg4/src/m4vencode.c"
SOURCE_FILES =+ "external/flv2mpeg4/src/flvdecoder.c"
SOURCE_FILES =+ "external/flv2mpeg4/src/dcprediction.c"
SOURCE_FILES =+ "external/flv2mpeg4/src/flv2mpeg4.c"

SOURCE_FILES =+ "${@bb.utils.contains("TARGET_ARCH", "sh4", "\
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
output/writer/mipsel/mp3.c \
output/writer/mipsel/pcm.c \
output/writer/mipsel/lpcm.c \
output/writer/mipsel/dts.c \
output/writer/mipsel/amr.c \
output/writer/mipsel/wma.c \
output/writer/mipsel/h265.c \
output/writer/mipsel/h264.c \
output/writer/mipsel/h263.c \
output/writer/mipsel/mpeg2.c \
output/writer/mipsel/mpeg4.c \
output/writer/mipsel/divx3.c \
output/writer/mipsel/vp.c \
output/writer/mipsel/wmv.c \
output/writer/mipsel/vc1.c ", d)}"

do_compile() {
    ${CC} ${SOURCE_FILES} -D_FILE_OFFSET_BITS=64 -D_LARGEFILE64_SOURCE -D_LARGEFILE_SOURCE -DHAVE_FLV2MPEG4_CONVERTER -I${S}/include -I${S}/external -I${S}/external/flv2mpeg4 -I${D}/${libdir} -I${D}/${includedir} -lpthread -lavformat -lavcodec -lavutil -lswresample -o exteplayer3 ${LDFLAGS}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/exteplayer3 ${D}${bindir}
}

INSANE_SKIP_${PN} += "ldflags"
