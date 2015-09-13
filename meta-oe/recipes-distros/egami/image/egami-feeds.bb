SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r6"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    curlftpfs \
    cdfs \
    oe-alliance-skins \
    enigma2-display-skins \
    enigma2-skins-openvix \
    openatv-picons-meta \
    enigma2-skins \
    egami-bootlogos-meta \
    enigma2-plugin-skins.egami-egarmymoodblue \
    enigma2-plugin-skins.egami-edreamy \
    enigma2-plugin-skins.egami-edreamyfhd \
    enigma2-plugin-skins.egami-a4you \
    enigma2-plugin-skins-metrix-atv \
    enigma2-plugin-skins-opendroid \
    enigma2-plugin-skins-nobile \
    enigma2-plugin-skins-pax \
    enigma2-plugin-skincomponents-egskinscomponents \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-bmediacenter \
    ${@base_contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    "
