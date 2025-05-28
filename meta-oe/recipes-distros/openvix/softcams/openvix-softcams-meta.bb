SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openvix-softcams"

DEPENDS = "\
    enigma2-plugin-softcams-oscam-emu \
    enigma2-plugin-softcams-oscam-latest \
    \    
    enigma2-plugin-softcams-oscam-pcscd-latest \
    \
    enigma2-plugin-softcams-ncam \
    \
    ${@bb.utils.contains("MACHINE_FEATURES", "hisil-3716mv430", "enigma2-plugin-softcams-cccam82", "enigma2-plugin-softcams-cccam", d)} \    
    "

PR = "r16"
