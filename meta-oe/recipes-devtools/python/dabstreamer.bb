DESCRIPTION = "DAB Streamer for Enigma2 using tsniv2ni and modified ni2http"
SECTION = "devel/python"
MAINTAINER = "SatDreamGR"
HOMEPAGE = "www.satdreamgr.com"
inherit allarch

require conf/license/license-gplv2.inc
RDEPENDS_${PN} = "eti-tools ${PYTHON_PN}-core tsniv2ni"

SRC_URI = "git://github.com/satdreamgr/dabstreamer.git;protocol=https;branch=master"
S = "${WORKDIR}/git"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"

PACKAGES = "${PN}"

do_install:append() {
	install -d ${D}${sbindir}
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/dabstreamer ${D}${sysconfdir}/init.d
	ln -sf ${sysconfdir}/init.d/dabstreamer ${D}${sbindir}/dabstreamer
}

FILES_${PN} = "/"

INITSCRIPT_NAME = "dabstreamer"
INITSCRIPT_PARAMS = "defaults 50"

inherit update-rc.d
