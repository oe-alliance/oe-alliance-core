SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r2"

RRECOMMENDS_${PN} = "\
    enigma2-display-skins \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    "
