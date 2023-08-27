SUMMARY = "Portable SDK for UPnP* Devices"
DESCRIPTION = "The Portable SDK for UPnP Devices is an SDK for development of \
UPnP device and control point applications. It consists of the core UPnP \
protocols along with a UPnP-specific eXtensible Markup Language (XML) parser \
supporting the Document Object Model (DOM) Level 2 API and an optional, \
integrated mini web server for serving UPnP related documents."
HOMEPAGE = "http://pupnp.sourceforge.net/"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=394a0f17b97f33426275571e15920434"

SRC_URI = "${SOURCEFORGE_MIRROR}/pupnp/libupnp-${PV}.tar.bz2 \
           file://0001-ithread-Use-pthread_mutexattr_gettype-pthread_mutexa.patch \
"

SRC_URI[md5sum] = "05c2393eee4fbf81c9e1b116b9554039"
SRC_URI[sha256sum] = "3168f676352e2a6e45afd6ea063721ed674c99f555394903fbd23f7f54f0a503"

S = "${WORKDIR}/libupnp-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF += "--enable-reuseaddr"

# Enable LFS support ( for samples )
CFLAGS += "-D_LARGEFILE_SOURCE -D_FILE_OFFSET_BITS=64 -D_GNU_SOURCE"
CXXFLAGS += "-D_LARGEFILE_SOURCE -D_FILE_OFFSET_BITS=64 -D_GNU_SOURCE"
