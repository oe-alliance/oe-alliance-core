SUMMARY = "OpenViX Extras"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r3"

inherit packagegroup

DEPENDS = "\
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-systemplugins-quadpip \
    enigma2-skins-openvix \
    oe-alliance-skins \
    openvix-bootlogos-meta \
    openvix-softcams-meta \
    ${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "", "enigma2-display-skins", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    "
