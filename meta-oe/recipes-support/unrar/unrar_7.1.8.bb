SUMMARY = "RAR archivers"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://license.txt;md5=fc9c335ec05a5f36764ef9ce7a79daa1"

HOMEPAGE = "http://www.rarlab.com/"

SRC_URI = "https://ftp.osuosl.org/pub/blfs/conglomeration/unrarsrc/unrarsrc-${PV}.tar.gz \
        file://makefile-nostrip.patch \
"
SRC_URI[sha256sum] = "9ec7765a948140758af12ed29e3e47db425df79a9c5cbb71b28769b256a7a014"

inherit upx-compress

S = "${UNPACKDIR}/unrar"

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

EXTRA_OEMAKE = "-f makefile DESTDIR=${D}${exec_prefix}"

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install
}

INSANE_SKIP:${PN} = "already-stripped ldflags"
