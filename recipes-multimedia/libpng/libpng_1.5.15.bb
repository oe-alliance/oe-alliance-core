DESCRIPTION = "PNG Library"
HOMEPAGE = "http://www.libpng.org/"
LICENSE = "libpng"
SECTION = "libs"
PRIORITY = "required"
LICENSE = "Libpng"
LIC_FILES_CHKSUM = "file://LICENSE;md5=58c8238139ee86082f8d29eb89304241"

DEPENDS = "zlib"

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/libpng/libpng15/${PV}/libpng-${PV}.tar.gz"
S = "${WORKDIR}/libpng-${PV}"

inherit autotools pkgconfig binconfig

SRC_URI[md5sum] = "ea24254980fd820964a710e4d2a947c7"
SRC_URI[sha256sum] = "726224b7a6b5ad0032078bf3fb5a84ffb5ad683a33a62d67f7be5eb5bc37d076"

PACKAGES =+ "${PN}15"

FILES_${PN}15 = "${libdir}/libpng15${SOLIBS}"
FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev += " ${bindir} ${sbindir}"
RPROVIDES_${PN}-dev += "${PN}15-dev"

BBCLASSEXTEND = "native"
