SUMMARY = "Manage OpenViX-HD skins."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "5"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS_${PN} = "\
    enigma2-plugin-skins1080-youvix-blue \
    enigma2-plugin-skins1080-youvix-red \
    enigma2-plugin-skins1080-youvix-green \
    enigma2-plugin-skins1080-youvix-purple \
    enigma2-plugin-skinpacks-dreamplex-youplex-blue \
    enigma2-plugin-skinpacks-dreamplex-youplex-red \
    enigma2-plugin-skinpacks-dreamplex-youplex-green \
    enigma2-plugin-skinpacks-dreamplex-youplex-purple \
    enigma2-plugin-skins1080-vixbmc-1080 \
    enigma2-plugin-skins1080-vixbmc-1080-bello \
    enigma2-plugin-skins1080-metrixhd-1080 \
    "
