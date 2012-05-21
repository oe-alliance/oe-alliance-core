DESCRIPTION = "RAR archivers"
LICENSE = "GPL"

LIC_FILES_CHKSUM = "file://license.txt;md5=09456acade1409c8aa4f0506840aa4f4"

HOMEPAGE = "http://www.rarlab.com/"
PR = "r0"

SRC_URI = "http://www.rarlab.com/rar/unrarsrc-${PV}.tar.gz;name=unrar \
		file://makefile-nostrip.patch"
SRC_URI[unrar.md5sum] = "203bc1e43935124db9635262cfd00b3f"
SRC_URI[unrar.sha256sum] = "7f61b0ef2a173f5a16daab53eaa838cc8fd6ec8cc72ce5f5b8cebe8e33c6e403"

S = "${WORKDIR}/unrar"

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

EXTRA_OEMAKE = "-f makefile.unix DESTDIR=${D}${exec_prefix}"

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}
