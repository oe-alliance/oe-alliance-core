SUMMARY = "enigma2 dhcp wait helper"
DESCRIPTION = "fix issue for slow dhcp server with softcams"
SECTION = "base"
require conf/license/license-gplv2.inc

SRC_URI = "file://enigma2_dhcp_wait.sh"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${UNPACKDIR}/enigma2_dhcp_wait.sh ${D}/usr/bin/enigma2_dhcp_wait.sh
}

do_package_qa() {
}

FILES:${PN} += " /usr/bin"
