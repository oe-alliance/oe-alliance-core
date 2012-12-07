SUMMARY = "create DVD-Video file system"
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "freetype libdvdread libfribidi libpng libxml2 zlib"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PN}/${P}.tar.gz"
SRC_URI[md5sum] = "2694a5a3ef460106ea3caf0f7f60ff80"
SRC_URI[sha256sum] = "501fb11b09c6eb9c5a229dcb400bd81e408cc78d34eab6749970685023c51fe9"

S = "${WORKDIR}/${PN}"

inherit autotools

EXTRA_OECONF = " \
        ac_cv_prog_MAGICKCONFIG= \
        ac_cv_prog_GMAGICKCONFIG= \
"
