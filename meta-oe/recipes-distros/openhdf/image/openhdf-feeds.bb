SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r16"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    openhdf-3rdparty-plugins \
    enigma2-skins \
    cdfs \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-skins-army-mod \
    enigma2-plugin-skins-nblack51-hdfmod \
    "
