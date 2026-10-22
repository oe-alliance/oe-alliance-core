SUMMARY = "Library for reading metadata from media files"
DESCRIPTION = "MediaInfoLib is a library used for retrieving technical information and other \
               metadata about audio or video files."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/MediaInfoLib/LICENSE;md5=9e35b82c62a9516c3b6f9846aac29fd0"

DEPENDS += "libtinyxml2 libzen zlib"

SRC_URI = "https://mediaarea.net/download/source/libmediainfo/${PV}/libmediainfo_${PV}.tar.bz2"
SRC_URI[md5sum] = "f15b7ebc2fab7f54142f6a498184ed3f"
SRC_URI[sha256sum] = "ba40d76b365053d9aeb7bd98d852993acaa53b9d63a3d84bddedd90150dcdbf8"

S = "${UNPACKDIR}/MediaInfoLib/Project/GNU/Library"

inherit autotools-brokensep pkgconfig
