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
SRC_URI[md5sum] = "ff4c9e8bee1c285ac791185de227592a"
SRC_URI[sha256sum] = "5c6426ad6f42e724e029f4de479b17fd5ac541ce9e07833fdf80dbc3ac24f68f"

S = "${UNPACKDIR}/MediaInfo/Project/GNU/CLI"

inherit autotools-brokensep pkgconfig
