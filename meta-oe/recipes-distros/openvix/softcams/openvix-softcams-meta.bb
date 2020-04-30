SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvix-softcams"

DEPENDS = "\
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-cccam", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-evocamd", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd135a", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd138", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd145c", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-newcs", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-mipsel" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-latest-mipsel" , "openvix-softcams-oscam-latest-arm", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-mipsel" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-latest-mipsel" , "openvix-softcams-oscam-pcscd-latest-arm", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-emu-mipsel" , "openvix-softcams-oscam-emu-arm", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-rqcamd", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-scam", "", d)} \
    "

PR = "r14"
