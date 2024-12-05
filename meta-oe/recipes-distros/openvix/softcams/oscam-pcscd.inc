SRCREV = "${AUTOREV}"
CAMNAME = "oscam-pcscd-latest"
SUMMARY:${PN} = "OSCam-pcscd-latest ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- pscd latest trunk"
PR = "r1"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"

SRC_URI += "git://git.streamboard.tv/common/oscam.git;protocol=https;branch=master"
