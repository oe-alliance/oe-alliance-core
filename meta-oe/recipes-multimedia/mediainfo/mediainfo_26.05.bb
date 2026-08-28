SUMMARY = "Mediainfo is a tool to analyze multimedia files"
DESCRIPTION = "MediaInfo is a convenient unified display of the most relevant \
               technical and tag data for video and audio files."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "console/utils"
PRIORIITY = "optional"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/MediaInfo/LICENSE;md5=9e35b82c62a9516c3b6f9846aac29fd0"

DEPENDS += "libmediainfo libzen zlib"

RDEPENDS:${PN} += "libmediainfo libzen"

SRC_URI = "https://mediaarea.net/download/source/mediainfo/${PV}/mediainfo_${PV}.tar.bz2"
SRC_URI[md5sum] = "d1c012f87a5ac0787c7025ab07d84df0"
SRC_URI[sha256sum] = "fdf80b0ed37032091f066ce0c145765a5bc5b3be7b6e4a38d94ca7d96f77e2e6"

S = "${UNPACKDIR}/MediaInfo/Project/GNU/CLI"

inherit autotools-brokensep pkgconfig
