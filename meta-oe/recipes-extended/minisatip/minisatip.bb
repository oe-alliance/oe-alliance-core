SUMMARY = "SAT>IP server"
MAINTAINER = "catalinii"
require conf/license/license-gplv2.inc

HOMEPAGE = "https://minisatip.org/"
DEPENDS = "libdvbcsa openssl"
RDEPENDS:${PN} = "libdvbcsa openssl"

SRC_URI = " \
    git://github.com/catalinii/minisatip.git;protocol=http;branch=master;protocol=https \
    file://minisatip.init \
    "

SRCREV = "${AUTOREV}"
PV = "V1.3+git"
PKGV = "V1.3+git${GITPKGV}"

S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/git"

inherit gitpkgv autotools-brokensep

INITSCRIPT_NAME = "minisatip"
EXTRA_OECONF = "--disable-netcv"

do_configure:prepend () {
}

do_install () {
    install -d -m 0755 ${D}/${bindir}
    install -d -m 0755 ${D}/${datadir}/${PN}
    install -d -m 0755 ${D}/etc/init.d
    install -m 0755 ${S}/minisatip ${D}/${bindir}/
    install -m 0755 ${WORKDIR}/minisatip.init ${D}/etc/init.d/minisatip
    cp -r --preserve=timestamps ${S}/html ${D}/${datadir}/${PN}
}
