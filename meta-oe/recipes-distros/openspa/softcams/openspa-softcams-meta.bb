SUMMARY = "meta file for enigma2 softcam packages"

require conf/license/license-gplv2.inc

PROVIDES = "openspa-softcams"

# mipsel only binary softcams (mips32el and mips32el-nf arch)
DEPENDS:append:mipsel = "\
    cam-cccam221 \
    cam-cccam230 \
    cam-cccam232 \
    cam-cccam239 \
    "

DEPENDS += "\
    cam-oscam-emu \
    cam-oscam-latest \
    cam-oscam-pcscd-latest \
    cam-ncam \
    cam-cccam-config \
    ${@bb.utils.contains("MACHINE_FEATURES", "hisil-3716mv430", "cam-cccam82", "cam-cccam232 cam-cccam239 cam-wicardd",  d)} \
    "

PR = "r1"
