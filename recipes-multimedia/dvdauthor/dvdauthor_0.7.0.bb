SUMMARY = "create DVD-Video file system"
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "freetype libdvdread libfribidi libpng libxml2 zlib"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PN}/${P}.tar.gz \
           file://dvdauthor_toplevel_video_format.patch"
SRC_URI[md5sum] = "33a447fb98ab3293ac40f869eedc17ff"
SRC_URI[sha256sum] = "aea6af7b99eba38ffa9dc5ad1521c2cc652e043cea0e7482e239d2a3fc2f34d0"

S = "${WORKDIR}/${PN}"

inherit autotools

EXTRA_OECONF = " \
        ac_cv_prog_MAGICKCONFIG= \
        ac_cv_prog_GMAGICKCONFIG= \
"
