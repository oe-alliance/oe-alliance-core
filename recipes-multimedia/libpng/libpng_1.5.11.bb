DESCRIPTION = "PNG Library"
HOMEPAGE = "http://www.libpng.org/"
LICENSE = "libpng"
SECTION = "libs"
PRIORITY = "required"
LICENSE = "Libpng"
LIC_FILES_CHKSUM = "file://LICENSE;md5=769a67aea75568083a4c21f7b36752ca"

DEPENDS = "zlib"

PR = "r0"

SRC_URI = "ftp://ftp.simplesystems.org/pub/png/src/libpng-${PV}.tar.bz2;name=libpng"
S = "${WORKDIR}/libpng-${PV}"

inherit autotools pkgconfig binconfig

SRC_URI[libpng.md5sum] = "cc1f78c87292161464c6f6f51fa19428"
SRC_URI[libpng.sha256sum] = "a2919797897d7159cec15a7305f668e0e50993c019d6716515aa8ec831e8704d"

PACKAGES =+ "${PN}15"

FILES_${PN}15 = "${libdir}/libpng15${SOLIBS}"
FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev += " ${bindir} ${sbindir}"
RPROVIDES_${PN}-dev += "${PN}15-dev"

BBCLASSEXTEND = "native"
