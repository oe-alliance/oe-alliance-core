SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r0"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    enigma2-pliplugins \
    enigma2-display-skins \
    oe-alliance-skins \
    enigma2-skins \
    enigma2-plugin-systemplugins-crossepg \
    cdfs \
    openssl-old \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-sdgradio \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    "
