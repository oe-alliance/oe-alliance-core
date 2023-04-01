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
SRC_URI[md5sum] = "9cf3a8eb17ef730d648abbe7d1ca035c"
SRC_URI[sha256sum] = "2556d7b6f6ce61eeaa9e713a7a2d61fd97fa7635b26fcfdc4f7d59e89e1a42eb"

S = "${WORKDIR}/MediaInfo/Project/GNU/CLI"

inherit autotools-brokensep pkgconfig
