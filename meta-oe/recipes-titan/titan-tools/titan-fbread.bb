SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/tools;module=fbread;protocol=http"

DEPENDS = " \
	libpng \
	jpeg \
	libusb \
	"

S = "${WORKDIR}"

CFLAGS:append:sh4 = " -DSH4"
CFLAGS:append:mipsel = " -DMIPSEL"
CFLAGS:append:arm = " -DARM"

do_compile() {
	cd ${WORKDIR}/fbread
	if [ ${TARGET_ARCH} = "sh4" ];then
		${CC} -Os -c fbread.c -o fbread.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	else
		${CC} -Os -c fbread.c -o fbread.o -I${STAGING_DIR_TARGET}/usr/include -I${STAGING_DIR_TARGET}/usr -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict ${CFLAGS}
	fi
	${CC} -Os fbread.o -L${STAGING_DIR_TARGET}/usr/lib -ljpeg -lpng -lusb-1.0 -lz -o fbread
}

FILES:${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 fbread/fbread ${D}/sbin/fbread
}
do_install[vardepsexclude] += "DATETIME"
