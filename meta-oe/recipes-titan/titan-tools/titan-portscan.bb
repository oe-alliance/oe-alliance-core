SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "network"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/tools;module=portscan;protocol=http"

S = "${WORKDIR}"

do_compile() {
	cd ${WORKDIR}/portscan
	${CC} portscan.c -O2 -lpthread -o portscan
}

FILES:${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 portscan/portscan ${D}/sbin/portscan
}
do_install[vardepsexclude] += "DATETIME"
