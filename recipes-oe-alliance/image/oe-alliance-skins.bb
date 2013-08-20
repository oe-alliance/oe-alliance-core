DESCRIPTION = "Manage skins for OE-Alliance feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r10"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS_${PN} = "\
    enigma2-plugin-skins-vix-day-hd \
    enigma2-plugin-skins-vix-night-hd \
    enigma2-plugin-skins-pli-hd \
    enigma2-plugin-skins-vix-magic-sd \
    enigma2-plugin-skins-vix-magic-hd \
    enigma2-plugin-skins-vix-magic-hd-noire \
    enigma2-plugin-skins-vix-magic-hd-night \
    enigma2-plugin-skins-vix-vixbmc-slim-hd \
    enigma2-plugin-skins-vix-vixbmc-night-hd \
    enigma2-plugin-skins-vix-vixbmc-metropolis \
    enigma2-plugin-skins-matrixhd \
    enigma2-plugin-skins-mynovum-hd \
    enigma2-plugin-skins-neonovum-hd \
    enigma2-plugin-skins-army-moodblue-hd \
    "
