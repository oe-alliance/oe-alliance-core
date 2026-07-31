SUMMARY = "UPnP media server"
RDEPENDS:${PN} = "mediatomb expat ffmpeg sqlite3 libexif js zlib file id3lib curl"
LICENSE = "LicenseRef-proprietary"

require conf/license/license-gplv2.inc

PV = "0.12.1"
PR = "r0"

S = "${UNPACKDIR}"

ALLOW_EMPTY:${PN} = "1"
