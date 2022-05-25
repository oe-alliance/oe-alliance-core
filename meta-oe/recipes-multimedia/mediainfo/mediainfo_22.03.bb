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

SRC_URI = "https://mediaarea.net/download/source/mediainfo/22.03/mediainfo_22.03.tar.bz2"
SRC_URI[md5sum] = "75757ea125647f43229e51e7dbd102c8"
SRC_URI[sha256sum] = "ea4ba633be25740b745498a4df943a56a994149e2ee4591a91488a6f2392c36c"

S = "${WORKDIR}/MediaInfo/Project/GNU/CLI"

inherit autotools-brokensep pkgconfig
