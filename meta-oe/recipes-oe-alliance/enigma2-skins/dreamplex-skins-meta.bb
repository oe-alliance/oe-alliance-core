SUMMARY = "Skins for Dreamplex"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r0"

inherit packagegroup

RDEPENDS:${PN} = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skinpacks-dreamplex-bluemod-fhd \
    enigma2-plugin-skinpacks-dreamplex-youplex-blue \
    enigma2-plugin-skinpacks-dreamplex-youplex-red \
    enigma2-plugin-skinpacks-dreamplex-youplex-green \
    enigma2-plugin-skinpacks-dreamplex-youplex-purple \
    enigma2-plugin-skinpacks-dreamplex-plex-experience \
    ", "", d)} \
    enigma2-plugin-skinpacks-dreamplex-bluemod  \
    "
