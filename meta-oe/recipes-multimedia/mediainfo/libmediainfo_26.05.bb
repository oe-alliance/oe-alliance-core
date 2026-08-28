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
SRC_URI[md5sum] = "991729d962a6fc86ee1fe3b4e281218c"
SRC_URI[sha256sum] = "736222cb45966412f50276461b7cd50488794948063fadd39c9a675ba20a3f4a"

S = "${UNPACKDIR}/MediaInfoLib/Project/GNU/Library"

inherit autotools-brokensep pkgconfig
