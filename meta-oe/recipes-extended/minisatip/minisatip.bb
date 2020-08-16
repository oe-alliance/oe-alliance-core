SUMMARY = "SAT>IP server"
MAINTAINER = "catalinii"
require conf/license/license-gplv2.inc

HOMEPAGE = "https://minisatip.org/"
DEPENDS = "libdvbcsa openssl"
RDEPENDS_${PN} = "libdvbcsa openssl"

SRC_URI = " \
    git://github.com/catalinii/minisatip.git;protocol=http \
    file://minisatip.init \
    "

SRCREV = "${AUTOREV}"
UPSTREAMVERSION = "1.0d"
PV = "${UPSTREAMVERSION}+git${SRCPV}"

S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/git"

inherit autotools-brokensep

INITSCRIPT_NAME = "minisatip"
EXTRA_OECONF = "--enable-enigma --disable-netcv"

do_configure_prepend () {
}

do_install () {
    install -d -m 0755 ${D}/${bindir}
    install -d -m 0755 ${D}/${datadir}/${PN}
    install -d -m 0755 ${D}/etc/init.d
    install -m 0755 ${S}/minisatip ${D}/${bindir}/
    install -m 0755 ${WORKDIR}/minisatip.init ${D}/etc/init.d/minisatip
    cp -r --preserve=timestamps ${S}/html ${D}/${datadir}/${PN}
}
