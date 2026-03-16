SRCREV = "${AUTOREV}"
CAMNAME = "oscam-pcscd-latest"
SUMMARY:${PN} = "OSCam-pcscd ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- pcscd latest trunk"
PR = "r1"

inherit gittag

PKGV = "${GITPKGVTAG}"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"

SRC_URI += "git://git.streamboard.tv/common/oscam.git;protocol=https;branch=master"
