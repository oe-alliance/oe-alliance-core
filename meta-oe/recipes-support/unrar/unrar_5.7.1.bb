SUMMARY = "RAR archivers"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://license.txt;md5=fc9c335ec05a5f36764ef9ce7a79daa1"

HOMEPAGE = "http://www.rarlab.com/"

SRC_URI = "http://www.rarlab.com/rar/unrarsrc-${PV}.tar.gz \
        file://makefile-nostrip.patch"
SRC_URI[md5sum] = "1695c966688dd26f7ba34f6f9f279d0c"
SRC_URI[sha256sum] = "d208abcceecfee0084bb8a93e9b756319d906a3ac6380ee5d10285fb0ffc4d65"

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

INSANE_SKIP_${PN} = "already-stripped ldflags"
