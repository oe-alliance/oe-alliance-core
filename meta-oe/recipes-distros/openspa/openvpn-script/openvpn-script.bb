SUMMARY = "Script for automating key generation in OpenVPN"
MAINTAINER = "OpenSPA"
SECTION = "base"
LICENSE = "proprietary"

inherit allarch

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"

SRC_URI="file://setup_openvpn.sh"

do_install() {
    install -d ${D}/usr/bin/
    install -m 0755 ${WORKDIR}/setup_openvpn.sh ${D}/usr/bin/setup_openvpn.sh
}
