SUMMARY = "file transcoding util."
PRIORITY = "required"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://code.vuplus.com/git/filestreamproxy.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/src/filestreamproxy ${D}/usr/bin
}

