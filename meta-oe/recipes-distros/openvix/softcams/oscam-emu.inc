MAINTAINER = "oscam-emu"
SUMMARY:${PN} = "OSCam-emu ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- with emu support"

SRC_URI="git://github.com/oscam-emu/oscam-patched.git;protocol=https;branch=master"

CAMNAME = "oscam-emu"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "1.20+git"
PKGV = "1.20+git${GITPKGV}"
PR = "r2"

S = "${WORKDIR}/git"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
