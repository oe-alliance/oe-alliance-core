SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "network"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/tools;module=bouquet2m3u;protocol=http"

S = "${WORKDIR}"

do_compile() {
	cd ${WORKDIR}/bouquet2m3u
    if [ ${TARGET_ARCH} != "sh4" ];then
    	${CC} GO_bouquet2m3u.c -O2 -mhard-float -o bouquet2m3u
    else
    	${CC} GO_bouquet2m3u.c -O2 -o bouquet2m3u
    fi
}

FILES:${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 bouquet2m3u/bouquet2m3u ${D}/sbin/bouquet2m3u
}
do_install[vardepsexclude] += "DATETIME"
