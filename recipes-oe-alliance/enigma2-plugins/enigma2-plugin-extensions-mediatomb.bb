DESCRIPTION = "UPnP media server"
RDEPENDS_${PN} = "mediatomb expat ffmpeg sqlite3 libexif js zlib file id3lib"
LICENSE = "proprietary"

PV = "0.12.1"
PR = "r0"

S = "${WORKDIR}"

ALLOW_EMPTY_${PN} = "1"
