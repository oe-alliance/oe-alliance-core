SUMMARY = "Manage skins for OE-Alliance feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r12"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS_${PN} = "\
    enigma2-plugin-skins-vix-day-hd \
    enigma2-plugin-skins-vix-night-hd \
    enigma2-plugin-skins-vix-magic-sd \
    enigma2-plugin-skins-vix-magic-hd \
    enigma2-plugin-skins-vix-magic-hd-noire \
    enigma2-plugin-skins-vix-magic-hd-night \
    enigma2-plugin-skins-vix-vixbmc-slim-hd \
    enigma2-plugin-skins-vix-vixbmc-night-hd \
    enigma2-plugin-skins-vix-vixbmc-metropolis \
    enigma2-plugin-skins-pli-full-hd-night \
    enigma2-plugin-skins-matrixhd \
	enigma2-plugin-skins-blue-hd \
    enigma2-nou-skins \
    enigma2-plugin-skins-army-moodblue-hd \
    "
