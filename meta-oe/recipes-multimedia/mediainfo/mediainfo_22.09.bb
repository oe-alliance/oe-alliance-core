SUMMARY = "Mediainfo is a tool to analyze multimedia files"
DESCRIPTION = "MediaInfo is a convenient unified display of the most relevant \
               technical and tag data for video and audio files."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "utils"
PRIORIITY = "optional"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/MediaInfo/LICENSE;md5=8f93c85175cbc94ea160ad08cc157822"

DEPENDS += "libmediainfo libzen zlib"

RDEPENDS:${PN} += "libmediainfo libzen"

SRC_URI = "https://mediaarea.net/download/source/mediainfo/${PV}/mediainfo_${PV}.tar.bz2"
SRC_URI[md5sum] = "f7425bbaba310b0f489aae39b7a6494e"
SRC_URI[sha256sum] = "26bfed67861967e96c9e1f3077a8b462f4ff456d3fc7f1dfaf8a51f1339aed4c"

S = "${WORKDIR}/MediaInfo/Project/GNU/CLI"

inherit autotools-brokensep pkgconfig
