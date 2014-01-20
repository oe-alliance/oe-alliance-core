SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r7"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    enigma2-pliplugins \
    enigma2-skins \
    enigma2-plugin-skins-adriatic \
    enigma2-plugin-skins-adriatic32 \
    enigma2-plugin-skins-jules-black-hd \
    enigma2-plugin-skins-redheathd \
    enigma2-plugin-skins-horizon \
    enigma2-plugin-skins-vanity \
    enigma2-plugin-skins-gotham \
    "
