MAINTAINER = "oscam"
SUMMARY:${PN} = "OSCam-latest ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- latest trunk"

SRC_URI += "gitsm://github.com/oscam-mirror/oscam.git;protocol=https;branch=master"

CAMNAME = "oscam-latest"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

inherit gittag

SRCREV = "${AUTOREV}"

PKGV = "${GITPKGVTAG}"
PR = "r4"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-openvix"
