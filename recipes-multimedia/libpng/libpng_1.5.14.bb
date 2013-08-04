DESCRIPTION = "PNG Library"
HOMEPAGE = "http://www.libpng.org/"
LICENSE = "libpng"
SECTION = "libs"
PRIORITY = "required"
LICENSE = "Libpng"
LIC_FILES_CHKSUM = "file://LICENSE;md5=58c8238139ee86082f8d29eb89304241"

DEPENDS = "zlib"

PR = "r2"

SRC_URI = "${SOURCEFORGE_MIRROR}/libpng/libpng15/${PV}/libpng-${PV}.tar.gz"
S = "${WORKDIR}/libpng-${PV}"

inherit autotools pkgconfig binconfig

SRC_URI[md5sum] = "27e76e0223d654093ffeb2f3daa56cc3"
SRC_URI[sha256sum] = "22007f80f6b6d6ad2b5d37bc135dce6701a17a4d64aee6d46edd3898bfbb1018"

PACKAGES =+ "${PN}15"

FILES_${PN}15 = "${libdir}/libpng15${SOLIBS}"
FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev += " ${bindir} ${sbindir}"
RPROVIDES_${PN}-dev += "${PN}15-dev"

BBCLASSEXTEND = "native"
