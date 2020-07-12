DESCRIPTION = "Weather forecast for 5 days"
SUMMARY = "Weather MSN"
MAINTAINER = "Sirius"
LICENSE = "GPLv3+"
HOMEPAGE = "www.gisclub.tv"
require conf/license/license-gplv2.inc

inherit gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "1.3.+git${SRCPV}"
PKGV = "1.3.+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/enigma2-plugins.git;protocol=https"

FILES_${PN} = "/usr/lib/enigma2/"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions
	cp -r --preserve=mode,links ${S}/python/Plugins/Extensions/WeatherMSN ${D}/usr/lib/enigma2/python/Plugins/Extensions/
	chmod -R a+rX ${D}/usr/lib/enigma2/
}

