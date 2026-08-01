MAINTAINER = "oscam-pscd"
SUMMARY:${PN} = "OSCam-pcscd ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- pcscd latest trunk"

SRC_URI += "gitsm://github.com/oscam-mirror/oscam.git;protocol=https;branch=master"

CAMNAME = "oscam-pcscd-latest"

require oscam-pcscd-common.inc

inherit gittag

SRCREV = "${AUTOREV}"

PKGV = "${GITPKGVTAG}"
PR = "r2"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-pcscd-latest"
