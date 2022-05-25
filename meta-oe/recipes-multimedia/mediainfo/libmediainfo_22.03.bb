SUMMARY = "Library for reading metadata from media files"
DESCRIPTION = "MediaInfoLib is a library used for retrieving technical information and other \
               metadata about audio or video files."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${WORKDIR}/MediaInfoLib/LICENSE;md5=8f93c85175cbc94ea160ad08cc157822"

DEPENDS += "libtinyxml2 libzen zlib"

SRC_URI = "https://mediaarea.net/download/source/libmediainfo/22.03/libmediainfo_22.03.tar.bz2"
SRC_URI[md5sum] = "bfe0f488445696e64cd7dee069a74129"
SRC_URI[sha256sum] = "9d0a72e9782e343d964d0d3015cd3da0255384730a031907063d44c3fd0ab729"

S = "${WORKDIR}/MediaInfoLib/Project/GNU/Library"

inherit autotools-brokensep pkgconfig
