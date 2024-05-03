SUMMARY = "TuxTxt for Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "info"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://public:public@svn.dyndns.tv/svn/tools;module=tuxtxt;protocol=http"

DEPENDS = " \
	tuxtxt-enigma2 \
	"

S = "${WORKDIR}"

CFLAGS:append:sh4 = " -DSH4"
CFLAGS:append:mipsel = " -DMIPSEL"
CFLAGS:append:arm = " -DARM"

do_compile() {
	cd ${WORKDIR}/tuxtxt
	if [ ${TARGET_ARCH} = "sh4" ];then
		${CC} -Os -c tuxtxt.c -o tuxtxt.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	else
		${CC} -Os -c tuxtxt.c -o tuxtxt.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	fi
	${CC} -Os tuxtxt.o -L${STAGING_DIR_TARGET}/usr/lib -lpthread -ltuxtxt32bpp -ltuxtxt -lz -o tuxtxt
}

FILES:${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 tuxtxt/tuxtxt ${D}/sbin/tuxtxt
}
do_install[vardepsexclude] += "DATETIME"
