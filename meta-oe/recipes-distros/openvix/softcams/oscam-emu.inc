MAINTAINER = "oscam-emu"
SUMMARY_${PN} = "OSCam-emu ${PKGV}"
DESCRIPTION_${PN} = "OSCam Open Source Softcam\n \
- with emu support"

SRC_URI="git://github.com/oscam-emu/oscam-patched.git;protocol=git"

CAMNAME = "oscam-emu"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "1.20+git${SRCPV}"
PKGV = "1.20+git${GITPKGV}"
#PR = "r0"

S = "${WORKDIR}/git"

RPROVIDES_${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RREPLACES_${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RCONFLICTS_${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
