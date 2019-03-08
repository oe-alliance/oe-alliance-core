SUMMARY = "TuxTxt for Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "info"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.0+svnr${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"

SRC_URI = "svn://sbnc.dyndns.tv/svn/tools;module=tuxtxt;protocol=http"

DEPENDS = " \
	tuxtxt-enigma2 \
	"

S = "${WORKDIR}/"

CFLAGS_append_sh4 = " -DSH4"
CFLAGS_append_mipsel = " -DMIPSEL"
CFLAGS_append_arm = " -DARM"

do_compile() {
	cd ${WORKDIR}/tuxtxt
	if [ ${TARGET_ARCH} = "sh4" ];then
		${CC} -Os -c tuxtxt.c -o tuxtxt.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	else
		${CC} -Os -c tuxtxt.c -o tuxtxt.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	fi
	${CC} -Os tuxtxt.o -L${STAGING_DIR_TARGET}/usr/lib -lpthread -ltuxtxt32bpp -ltuxtxt -lz -o tuxtxt
}

FILES_${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 tuxtxt/tuxtxt ${D}/sbin/tuxtxt
}
do_install[vardepsexclude] += "DATETIME"
