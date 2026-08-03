SUMMARY = "SAT>IP server"
MAINTAINER = "catalinii"
require conf/license/license-gplv2.inc

HOMEPAGE = "https://minisatip.org/"
DEPENDS = "libdvbcsa openssl"
RDEPENDS:${PN} = "libdvbcsa openssl"

SRC_URI = " \
    git://github.com/catalinii/minisatip.git;protocol=https;branch=master \
    file://add-missing-execinfo-include.patch \
    file://minisatip.init \
    "

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

inherit gittag cmake

INITSCRIPT_NAME = "minisatip"

do_install () {
    install -d -m 0755 ${D}/${bindir}
    install -d -m 0755 ${D}/${datadir}/${PN}
    install -d -m 0755 ${D}/etc/init.d
    install -m 0755 ${B}/minisatip ${D}/${bindir}/
    install -m 0755 ${UNPACKDIR}/minisatip.init ${D}/etc/init.d/minisatip
    cp -r --preserve=timestamps ${S}/html ${D}/${datadir}/${PN}
}
