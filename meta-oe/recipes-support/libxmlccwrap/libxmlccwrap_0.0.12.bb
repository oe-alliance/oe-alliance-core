SUMMARY = "A small libxml2 c++ wrapper"
AUTHOR = "Jürgen Rinas <jrinas@gmx.de>"
HOMEPAGE = "http://www.ant.uni-bremen.de/whomes/rinas/libxmlccwrap/"
SECTION = "libs"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=fad9b3332be894bab9bc501572864b29"
DEPENDS = "libxml2"

SRC_URI = "http://www.ant.uni-bremen.de/whomes/rinas/libxmlccwrap/download/${BP}.tar.gz \
       file://dont_build_unneeded.patch \
       file://disable_libxslt.patch \
       file://fix_assignment_operator.patch \
       file://fix_new_autotools.patch"

SRC_URI[md5sum] = "9f8bbad3452d704603246273b2dda758"
SRC_URI[sha256sum] = "38fb5f75f8b7dad1c8d535cc7b18ea9f1603e14a8b9256a2f60cf721513dc299"

inherit autotools

CXXFLAGS_append = " -I=${includedir}/libxml2 "

FILES_${PN} = "${libdir}/${P}${SOLIBSDEV}"
FILES_${PN}-dev = "${includedir} ${libdir}/${PN}${SOLIBSDEV} ${libdir}/*.la"
