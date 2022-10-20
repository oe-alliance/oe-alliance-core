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
SRC_URI[md5sum] = "b1087fb2cbf3de4d7d5f7344d1f89bea"
SRC_URI[sha256sum] = "bd2772ab1ae4e375cdbbbdd114ad8471ebe67bf78c76e3812d66c7d462ed8ae7"

S = "${WORKDIR}/ZenLib/Project/GNU/Library"

inherit autotools-brokensep
