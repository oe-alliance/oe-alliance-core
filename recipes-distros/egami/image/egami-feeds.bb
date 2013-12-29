SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
ALLOW_EMPTY_${PN} = "1"
PV = "1.0"
PR = "r17"

inherit packagegroup

RCONFLICTS_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES_${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

RRECOMMENDS_${PN} = "\
    oe-alliance-skins \
    enigma2-display-skins \
    openatv-picons-meta \
    enigma2-skins \
    enigma2-pliplugins \
    enigma2-plugin-picons-default-ventonsupport \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
"