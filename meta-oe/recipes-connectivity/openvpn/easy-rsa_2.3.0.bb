SUMMARY = "Simple shell based CA utility"
DESCRIPTION = "This package eases the creation of certificates, for example for\nopenvpn clients.\n.\nThis was formerly part of the openvpn package."
HOMEPAGE = "https://community.openvpn.net/openvpn"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e944ef975ef9d0312e63c9ee80df17fc"
DEPENDS = "lzo openssl libpam"
RDEPENDS_{PN} = "openssl"

inherit autotools

PV = "2.3.0"
PKGV = "2.3.0"
#PR = "r0"

inherit allarch

SRC_URI="https://build.openvpn.net/downloads/releases/easy-rsa-${PV}_master.tar.gz"

SRC_URI[md5sum] = "2c0e3263327d51d6f3b3ac337cec27c4"
SRC_URI[sha256sum] = "35710bd7d884d8749af0432b26a95594cfb691f427fb672ec5c9a6eefaf01641"

S = "${WORKDIR}/easy-rsa-${PV}_master"

do_install() {
    install -d ${D}/${datadir}/easy-rsa
    rm -rf ${S}/easy-rsa/2.0/keys
    install -m 755 ${S}/easy-rsa/2.0/* ${D}/${datadir}/easy-rsa
}
