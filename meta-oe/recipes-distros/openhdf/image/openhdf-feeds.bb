SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r25"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    enigma2-skins \
    oe-alliance-skins \
    cdfs \
    enigma2-display-skins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-skins-army-mod \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-skins-nblack51-hdfmod \
    enigma2-plugin-skins-ax-blue-fhd-4hdf \
    enigma2-plugin-skins-blue-line-oct-4hdf \
    enigma2-plugin-skincomponents-bmediacenter-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-blue-line-skin \
    "
