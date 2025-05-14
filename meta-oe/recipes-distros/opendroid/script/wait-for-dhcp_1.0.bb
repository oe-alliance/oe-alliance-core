SUMMARY = "Enigma2 DHCP wait helper"
DESCRIPTION = "Fixes issues with slow DHCP servers when used with softcams"
SECTION = "base"
require conf/license/license-gplv2.inc

SRC_URI = "file://wait_for_dhcp.sh"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/wait_for_dhcp.sh ${D}/usr/bin/wait_for_dhcp.sh
}

do_package_qa() {
}

FILES:${PN} += " /usr/bin"
