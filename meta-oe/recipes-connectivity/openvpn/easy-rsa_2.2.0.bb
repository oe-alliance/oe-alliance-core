SUMMARY = "Simple shell based CA utility"
DESCRIPTION = "This package eases the creation of certificates, for example for\nopenvpn clients.\n.\nThis was formerly part of the openvpn package."
HOMEPAGE = "https://community.openvpn.net/openvpn"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e944ef975ef9d0312e63c9ee80df17fc"
DEPENDS = "lzo openssl libpam"
RDEPENDS_{PN} = "openssl"

inherit autotools

PV = "2.2.0"
PKGV = "${GITPKGVTAG}"
#PR = "r2"
PACKAGE_ARCH = "all"

SRC_URI="http://swupdate.openvpn.org/community/releases/easy-rsa-${PV}_master.tar.gz"

SRC_URI[md5sum] = "fbf818b6e1f212e77b9ce0e6d92584a1"
SRC_URI[sha256sum] = "d23ddc3a764b40d03ada76f387c92ae4dcf4f5266da54c2bae130325b05eebe2"

S = "${WORKDIR}/easy-rsa-${PV}_master"

do_install() {
    install -d ${D}/${datadir}/easy-rsa
    install -m 755 ${S}/easy-rsa/2.0/* ${D}/${datadir}/easy-rsa
}
