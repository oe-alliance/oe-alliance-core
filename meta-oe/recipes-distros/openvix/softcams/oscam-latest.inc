SRCREV = "${AUTOREV}"
CAMNAME = "oscam-latest"
SUMMARY:${PN} = "OSCam-latest ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- latest trunk"
PR = "r2"

RPROVIDES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RREPLACES:${PN} += "enigma2-plugin-softcams-oscam-openvix"
RCONFLICTS:${PN} += "enigma2-plugin-softcams-oscam-openvix"

SRC_URI += "svn://svn.streamboard.tv/oscam;protocol=https;module=trunk;scmdata=keep"
