SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r0"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openeight-feeds"

RRECOMMENDS_${PN} = "\
    openeight-version-info \
    enigma2-plugin-pli-softcamsetup \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-extraspanel \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-skinselector \
    enigma2-plugin-skins-octagoneightfhd \
    enigma2-plugin-skins-octagoneightsd \
    ${@bb.utils.contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    "
