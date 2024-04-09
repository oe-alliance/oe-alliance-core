SRCREV = "${AUTOREV}"
CAMNAME = "oscam-latest"
SUMMARY:${PN} = "OSCam-latest ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- latest trunk"
PR = "r3"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-openvix"

SRC_URI += "git://git.streamboard.tv/common/oscam.git;protocol=https;branch=master"
