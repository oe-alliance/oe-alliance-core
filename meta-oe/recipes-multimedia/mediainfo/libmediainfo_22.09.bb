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
SRC_URI[md5sum] = "269c4d61a4c305265513cd29dd455990"
SRC_URI[sha256sum] = "256e61e0cd9478bf6dbe2f54f137e01068ecaaf0e2f3901a8bcf4c28c2f21a5f"

S = "${WORKDIR}/MediaInfoLib/Project/GNU/Library"

inherit autotools-brokensep pkgconfig
