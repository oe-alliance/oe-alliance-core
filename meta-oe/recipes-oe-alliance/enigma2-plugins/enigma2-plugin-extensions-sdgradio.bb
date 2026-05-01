SUMMARY = "Enigma2 Software Defined Radio"
DESCRIPTION = "SDR for Enigma2 using rtl_fm and dab-cmdline command line tools"
SECTION = "multimedia"
MAINTAINER = "SatDreamGR"
HOMEPAGE = "http://satdreamgr.com"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "python3-core rtl-sdr redsea dab-cmdline-sdgradio dab-cmdline-sdgradio-pcm dab-cmdline-sdgradio-wav"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/SDGRadio.git;protocol=https;branch=main"

