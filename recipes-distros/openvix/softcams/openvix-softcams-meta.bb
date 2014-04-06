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
    openvix-softcams-mgcamd \
    openvix-softcams-newcs \
    openvix-softcams-rqcamd \
    openvix-softcams-scam \
    "

PR = "r3"
