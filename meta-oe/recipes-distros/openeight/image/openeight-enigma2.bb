SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r3"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openeight-feeds"

E2_LANGUAGES = "\
    enigma2-locale-ar \
    enigma2-locale-bg \
    enigma2-locale-ca \
    enigma2-locale-cs \
    enigma2-locale-da \
    enigma2-locale-de \
    enigma2-locale-el \
    enigma2-locale-en \
    enigma2-locale-en-gb \
    enigma2-locale-es \
    enigma2-locale-et \
    enigma2-locale-fa \
    enigma2-locale-fi \
    enigma2-locale-fr \
    enigma2-locale-fy \
    enigma2-locale-he \
    enigma2-locale-hk \
    enigma2-locale-hr \
    enigma2-locale-hu \
    enigma2-locale-id \
    enigma2-locale-is \
    enigma2-locale-it \
    enigma2-locale-ku \
    enigma2-locale-lt \
    enigma2-locale-lv \
    enigma2-locale-nb \
    enigma2-locale-nl \
    enigma2-locale-no \
    enigma2-locale-pl \
    enigma2-locale-pt \
    enigma2-locale-pt-br \
    enigma2-locale-ro \
    enigma2-locale-ru \
    enigma2-locale-sk \
    enigma2-locale-sl \
    enigma2-locale-sr \
    enigma2-locale-sv \
    enigma2-locale-th \
    enigma2-locale-tr \
    enigma2-locale-uk \
    enigma2-locale-zh \
    enigma2-locale-zh-cn \
    enigma2-locale-zh-hk \
    "

RRECOMMENDS_${PN} = "\
    openeight-version-info \
    enigma2-plugin-pli-softcamsetup \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-autobackup \
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
    enigma2-plugin-skins-smartlitefhd \
    enigma2-plugin-skins-smartlitesd \
    enigma2-plugin-systemplugins-servicemp3 \
    enigma2-plugin-systemplugins-satipclient \
    ${@bb.utils.contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${E2_LANGUAGES} \
    "
