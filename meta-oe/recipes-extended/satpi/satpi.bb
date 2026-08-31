SUMMARY = "An SAT>IP server for linux, suitable for running on an Raspberry Pi, VU+, BeagleBone or any other linux box."
MAINTAINER = "Marc Postema (mpostema09 -at- gmail.com)"
LICENSE = "GPL-2.0-or-later AND LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"

HOMEPAGE = "https://github.com/Barracuda09/SATPI/wiki"
DEPENDS = "libdvbcsa openssl"
RDEPENDS:${PN} = "libdvbcsa openssl"

SRC_URI = "git://github.com/Barracuda09/SATPI.git;protocol=https;branch=master \
    file://satpi.init \
"

SRCREV = "${AUTOREV}"
PV = "V1.6+git"
PKGV = "V1.6+git${GITPKGV}"

#BUILD = "${WORKDIR}/git"
#UNPACKDIR = "${S}"

inherit gitpkgv autotools-brokensep update-rc.d

INITSCRIPT_NAME = "satpi"
EXTRA_OEMAKE = " LIBDVBCSA=yes ENIGMA=yes BUILD=speed"

INITSCRIPT_PARAMS = "defaults 80"

CXXFLAGS += " -std=c++11"

do_configure[noexec] = "1"

do_install () {
    install -d -m 0755 ${D}${bindir}
    install -m 0755 ${S}/satpi ${D}${bindir}/
    install -d -m 0755 ${D}${INIT_D_DIR}
    install -d ${D}${datadir}/${PN}
    install -m 0755 ${UNPACKDIR}/satpi.init ${D}${INIT_D_DIR}/satpi
    cp -r --preserve=timestamps ${S}/web ${D}${datadir}/${PN}
    install -d ${D}${localstatedir}/lib/satpi
    install -m 644 ${S}/mapping.m3u ${D}${localstatedir}/lib/satpi
}

FILES:${PN} += "${localstatedir}/lib/satpi"
