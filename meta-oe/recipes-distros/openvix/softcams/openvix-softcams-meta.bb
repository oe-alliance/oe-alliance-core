SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvix-softcams"

DEPENDS = "\
    openvix-softcams-cccam \
    openvix-softcams-cccam209 \
    openvix-softcams-cccam221 \
    openvix-softcams-oscam \
    openvix-softcams-oscam-pcscd \
    openvix-softcams-evocamd \
    openvix-softcams-mgcamd135a \
    openvix-softcams-mgcamd138 \
    openvix-softcams-newcs \
    openvix-softcams-rqcamd \
    openvix-softcams-scam \
    "    

# Remove unavailable sh4 softcams
DEPENDS_sh4 = "\
    openvix-softcams-oscam \
    openvix-softcams-oscam-pcscd \
    "

PR = "r4"
