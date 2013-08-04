SUMMARY = "A collection of cryptographic algorithms and protocols"
SECTION = "devel/python"
LICENSE = "PD & Python-2.0"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=35f354d199e8cb7667b059a23578e63d"
DEPENDS = "gmp"
PR = "r1"

SRC_URI = "http://ftp.dlitz.net/pub/dlitz/crypto/pycrypto/pycrypto-${PV}.tar.gz \
           file://no-usr-include.patch"
SRC_URI[md5sum] = "783e45d4a1a309e03ab378b00f97b291"
SRC_URI[sha256sum] = "e950a78184e2a7defccf5d45e0c29c1e9edeb29984433f0d110a21e9631e38de"

S = "${WORKDIR}/pycrypto-${PV}"

inherit autotools distutils

BBCLASSEXTEND = "native"
