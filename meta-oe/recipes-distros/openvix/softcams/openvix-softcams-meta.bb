SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvix-softcams"

DEPENDS = "\
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-cccam", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-cccam209", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-cccam221", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-evocamd", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd135a", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd138", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-newcs", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-mipsel" , "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-latest-mipsel" , "openvix-softcams-oscam-latest-arm", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-mipsel" , "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-latest-mipsel" , "openvix-softcams-oscam-pcscd-latest-arm", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-rqcamd", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams-scam", "", d)} \
    "    

PR = "r10"
