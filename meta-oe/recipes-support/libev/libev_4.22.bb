DESCRIPTION = "Event Loop Library"
HOMEPAGE = "http://software.schmorp.de/pkg/libev.html"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6ad416afd040c90698edcdf1cbee347"
SRC_URI = "http://dist.schmorp.de/libev/libev-${PV}.tar.gz"

inherit autotools

SRC_URI[md5sum] = "bf0007e37240103d3c2be80ca9bee3f9"
SRC_URI[sha256sum] = "736079e8ac543c74d59af73f9c52737b3bfec9601f020bf25a87a4f4d0f01bd6"

do_compile_prepend() {
	sed -i 's#include_HEADERS = ev.h ev++.h event.h#include_HEADERS = ev.h ev++.h#' ${S}/Makefile.*
	sed -i 's#libev_la_SOURCES = ev.c event.c#libev_la_SOURCES = ev.c#' ${S}/Makefile.*
}

