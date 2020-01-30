SUMMARY = "Enigma2 Software Defined Radio"
DESCRIPTION = "SDR for Enigma2 using rtl_fm and dab-cmdline command line tools"
SECTION = "multimedia"
MAINTAINER = "SatDreamGR"
HOMEPAGE = "http://satdreamgr.com"
LICENSE = "PD"

require conf/license/license-gplv2.inc

SRC_URI = "git://github.com/satdreamgr/SDGRadio.git;protocol=http"

S = "${WORKDIR}/git"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"
PR = "r3"

inherit distutils-openplugins

RDEPENDS_${PN} = "python-core rtl-sdr redsea dab-cmdline-sdgradio dab-cmdline-sdgradio-pcm dab-cmdline-sdgradio-wav"

RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/SDGRadio/*.py"

