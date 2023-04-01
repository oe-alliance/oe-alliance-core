SUMMARY = "ZenLib C++ utility library - small C++ derivate classes to have an easier life"
DESCRIPTION = "ZenLib is a C++ utility library. It includes classes for handling strings,\
               configuration, bit streams, threading, translation, and cross-platform \
               operating system functions."
HOMEPAGE = "https://mediaarea.net/en/MediaInfo"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://${WORKDIR}/ZenLib/License.txt;md5=3fc93316cd7abee66f851588fbcde985"

SRC_URI= "https://mediaarea.net/download/source/libzen/${PV}/libzen_${PV}.tar.bz2"
SRC_URI[md5sum] = "2e1e1018d33c8a957e72774a7f9d602b"
SRC_URI[sha256sum] = "5543e2c45214b9d9f0ba4f43dee61d0296e1ff9f1427ed6c879ddd1b62b9f69e"

S = "${WORKDIR}/ZenLib/Project/GNU/Library"

inherit autotools-brokensep
