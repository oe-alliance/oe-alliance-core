SUMMARY = "Enigma2 Software Defined Radio"
DESCRIPTION = "SDR for Enigma2 using rtl_fm and dab-cmdline command line tools"
SECTION = "multimedia"
MAINTAINER = "SatDreamGR"
HOMEPAGE = "http://satdreamgr.com"
LICENSE = "PD"

require conf/license/license-gplv2.inc

SRC_URI = "git://github.com/oe-mirrors/SDGRadio.git;protocol=http"

S = "${WORKDIR}/git"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1+git${SRCPV}"
PKGV = "1+git${GITPKGV}"
PR = "r3"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)}

RDEPENDS_${PN} = "${PYTHON_PN}-core rtl-sdr redsea dab-cmdline-sdgradio dab-cmdline-sdgradio-pcm dab-cmdline-sdgradio-wav"

