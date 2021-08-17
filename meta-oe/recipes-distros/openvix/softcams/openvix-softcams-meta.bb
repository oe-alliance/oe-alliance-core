SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvix-softcams"

DEPENDS = "\
    enigma2-plugin-softcams-oscam-emu \
    enigma2-plugin-softcams-oscam-stable \
    enigma2-plugin-softcams-oscam-latest \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-cccam", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-evocamd", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd135a", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd138", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-mgcamd145c", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-newcs", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-mipsel" , "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-oscam-pcscd-latest-mipsel" , "openvix-softcams-oscam-pcscd-latest-arm", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-rqcamd", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "mipsel", "openvix-softcams-scam", "", d)} \
    "

PR = "r14"
