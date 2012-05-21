CAMNAME = "oscam-experimental"
DESCRIPTION = "${CAMNAME} ${PV} Open Source Softcam"

RDEPENDS_${PN} = "${CAMNAME}"
RCONFLICTS_${PN} = "enigma2-plugin-softcams-${CAMNAME}-cs enigma2-plugin-softcams-oscam-stable-cs enigma2-plugin-softcams-oscam-unstable-cs"

PV = "1.20"
PR = "r4.${INC_PR}"

require softcamoscam.inc
