SUMMARY = "Gerbera - UPnP Media Server"
RDEPENDS:${PN} = "gerbera"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PV = "1.10.0"
PR = "r0"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

ALLOW_EMPTY:${PN} = "1"
