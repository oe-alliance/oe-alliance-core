SUMMARY = "Library for reading metadata from media files"
DESCRIPTION = "MediaInfoLib is a library used for retrieving technical information and other \
               metadata about audio or video files."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/MediaInfoLib/LICENSE;md5=8f93c85175cbc94ea160ad08cc157822"

DEPENDS += "libtinyxml2 libzen zlib"

SRC_URI = "https://mediaarea.net/download/source/libmediainfo/${PV}/libmediainfo_${PV}.tar.bz2"
SRC_URI[md5sum] = "dd166e6fa2cb2eafd3a783ccd4da3fcd"
SRC_URI[sha256sum] = "c1255fb8aa6a505fb3c738c731a327d4966f0e3cbb2bb3b4be89b9ee0263cb74"

S = "${WORKDIR}/MediaInfoLib/Project/GNU/Library"

inherit autotools-brokensep pkgconfig
