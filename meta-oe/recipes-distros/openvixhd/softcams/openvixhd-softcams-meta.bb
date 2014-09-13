SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvixhd-softcams"

DEPENDS = "\
    openvixhd-softcams-cccam \
    openvixhd-softcams-cccam209 \
    openvixhd-softcams-cccam221 \
    openvixhd-softcams-oscam \
    openvixhd-softcams-oscam-pcscd \
    openvixhd-softcams-evocamd \
    openvixhd-softcams-mgcamd135a \
    openvixhd-softcams-mgcamd138 \
    openvixhd-softcams-newcs \
    openvixhd-softcams-rqcamd \
    openvixhd-softcams-scam \
    "

# Remove unavailable sh4 softcams
DEPENDS_sh4 = "\
    openvix-softcams-oscam \
    openvix-softcams-oscam-pcscd \
    "

PR = "r4"
