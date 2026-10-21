MAINTAINER = "oscam-emu"
SUMMARY:${PN} = "OSCam-emu ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- with emu support"

SRC_URI += "gitsm://github.com/oscam-mirror/oscam-emu.git;protocol=https;branch=master"
SRC_URI:append:openvix = " file://oscam-emu-stale-ecm-info.patch"

CAMNAME = "oscam-emu"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require oscam-common.inc

inherit gittag

SRCREV = "${AUTOREV}"

PKGV = "${GITPKGVTAG}"
PR = "r5"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-openvix-emu"
