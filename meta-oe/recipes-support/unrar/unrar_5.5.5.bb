SUMMARY = "RAR archivers"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://license.txt;md5=fc9c335ec05a5f36764ef9ce7a79daa1"

HOMEPAGE = "http://www.rarlab.com/"
PR = "r0"

SRC_URI = "http://www.rarlab.com/rar/unrarsrc-${PV}.tar.gz \
        file://makefile-nostrip.patch"
SRC_URI[md5sum] = "d741dfe5f09bc24679ac5d0158c88f20"
SRC_URI[sha256sum] = "a4553839cb2f025d0d9c5633816a83a723e3938209f17620c8c15da06ed061ef"

S = "${WORKDIR}/unrar"

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

EXTRA_OEMAKE = "-f makefile DESTDIR=${D}${exec_prefix}"

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}
