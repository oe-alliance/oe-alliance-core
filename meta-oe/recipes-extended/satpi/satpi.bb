SUMMARY = "An SAT>IP server for linux, suitable for running on an Raspberry Pi, VU+, BeagleBone or any other linux box."
MAINTAINER = "Marc Postema (mpostema09 -at- gmail.com"
LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"

HOMEPAGE = "https://github.com/Barracuda09/SATPI/wiki"
DEPENDS = "libdvbcsa openssl"
RDEPENDS_${PN} = "libdvbcsa openssl"

SRC_URI = "git://github.com/Barracuda09/SATPI.git;protocol=http \
    file://satpi.init \
"

SRCREV = "fc9e34ea4518eaecdd52f1fc2f6e9f2b3de4181d"
PV = "git${SRCPV}"

S = "${WORKDIR}/git"
BUILD = "${WORKDIR}/git"

inherit autotools-brokensep update-rc.d

INITSCRIPT_NAME = "satpi"
EXTRA_OECONF = "LIBDVBCSA=yes ENIGMA=yes debug "

INITSCRIPT_PARAMS = "defaults 80"

CXXFLAGS = " -std=c++11"

do_configure_prepend () {
}

do_install () {
    install -d -m 0755 ${D}/${bindir}
    install -m 0755 ${S}/satpi ${D}/${bindir}/
    install -d -m 0755 ${D}/etc/init.d
    install -d ${D}/${datadir}/${PN}
    install -m 0755 ${S}/satpi ${D}/${bindir}/
    install -m 0755 ${WORKDIR}/satpi.init ${D}/etc/init.d/satpi
    cp -r --preserve=timestamps ${S}/web ${D}/${datadir}/${PN}
    cp -r --preserve=timestamps ${S}/mapping.m3u ${D}/${bindir}
}
