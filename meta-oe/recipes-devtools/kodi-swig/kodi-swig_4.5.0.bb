SUMMARY = "SWIG 4.5 host tool for Kodi 22"
DESCRIPTION = "A private native SWIG build used by Kodi without changing the SWIG version selected for other OpenEmbedded recipes."
HOMEPAGE = "https://www.swig.org/"
LICENSE = "BSD-3-Clause AND GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e7807a6282784a7dde4c846626b08fc6 \
                    file://LICENSE-GPL;md5=d32239bcb673463ab874e80d47fae504 \
                    file://LICENSE-UNIVERSITIES;md5=8ce9dcc8f7c994de4a408b205c72ba08"

SECTION = "devel"

DEPENDS = "libpcre2 bison-native"

SRC_URI = "${SOURCEFORGE_MIRROR}/swig/swig-${PV}.tar.gz \
           file://determinism.patch \
          "
SRC_URI[sha256sum] = "22ae0e887f8cca8031a325c67d005207653200b40e71edb3f88780e28e47d0ff"

S = "${UNPACKDIR}/swig-${PV}"

inherit cmake pkgconfig

BBCLASSEXTEND = "native"

def swiglib_relpath(d):
    swiglib = d.getVar('datadir') + "/swig/" + d.getVar('PV')
    return os.path.relpath(swiglib, d.getVar('bindir'))

do_install:append:class-native() {
    create_wrapper ${D}${bindir}/swig SWIG_LIB='`dirname $''realpath`'/${@swiglib_relpath(d)}
}
