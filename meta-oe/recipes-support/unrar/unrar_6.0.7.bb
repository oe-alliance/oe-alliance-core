SUMMARY = "RAR archivers"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://license.txt;md5=fc9c335ec05a5f36764ef9ce7a79daa1"

HOMEPAGE = "http://www.rarlab.com/"

SRC_URI = "https://ftp.osuosl.org/pub/blfs/conglomeration/unrarsrc/unrarsrc-${PV}.tar.gz \
        file://makefile-nostrip.patch"
SRC_URI[md5sum] = "de5017a63a610cb82dba7d33bd826fb6"
SRC_URI[sha256sum] = "a7029942006cbcced3f3b7322ec197683f8e7be408972ca08099b196c038f518"

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
