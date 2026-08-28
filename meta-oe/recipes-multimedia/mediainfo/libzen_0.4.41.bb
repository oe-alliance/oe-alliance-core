SUMMARY = "ZenLib C++ utility library - small C++ derivate classes to have an easier life"
DESCRIPTION = "ZenLib is a C++ utility library. It includes classes for handling strings,\
               configuration, bit streams, threading, translation, and cross-platform \
               operating system functions."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/ZenLib/License.txt;md5=3fc93316cd7abee66f851588fbcde985"

SRC_URI = "https://mediaarea.net/download/source/libzen/${PV}/libzen_${PV}.tar.bz2"
SRC_URI[md5sum] = "2f2f7f7a2d5f99b7a420576c4491003f"
SRC_URI[sha256sum] = "eb237d7d3dca6dc6ba068719420a27de0934a783ccaeb2867562b35af3901e2d"

S = "${UNPACKDIR}/ZenLib/Project/GNU/Library"

inherit autotools-brokensep
