SUMMARY = "UPnP media server"
RDEPENDS:${PN} = "gerbera ffmpeg file id3lib"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PV = "1.11.0"
PR = "r0"

S = "${WORKDIR}"

ALLOW_EMPTY:${PN} = "1"
