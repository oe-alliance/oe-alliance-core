SUMMARY = "SAT>IP server"
MAINTAINER = "catalinii"
require conf/license/license-gplv2.inc

HOMEPAGE = "https://minisatip.org/"
DEPENDS = "libdvbcsa openssl"
RDEPENDS:${PN} = "libdvbcsa openssl"

SRC_URI = " \
    git://github.com/catalinii/minisatip.git;protocol=http;branch=master;protocol=https \
    file://add-missing-execinfo-include.patch \
    file://minisatip.init \
    "

SRCREV = "${AUTOREV}"
PV = "V2.0+git"
PKGV = "V2.0+git${GITPKGV}"

S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/git"

inherit gitpkgv autotools-brokensep

INITSCRIPT_NAME = "minisatip"
EXTRA_OECONF = "--disable-netcv"

do_configure:prepend () {
    sed -i 's/-std=c++23/-std=c++20/g' ${S}/configure.ac
}

do_install () {
    install -d -m 0755 ${D}/${bindir}
    install -d -m 0755 ${D}/${datadir}/${PN}
    install -d -m 0755 ${D}/etc/init.d
    install -m 0755 ${S}/minisatip ${D}/${bindir}/
    install -m 0755 ${UNPACKDIR}/minisatip.init ${D}/etc/init.d/minisatip
    cp -r --preserve=timestamps ${S}/html ${D}/${datadir}/${PN}
}
