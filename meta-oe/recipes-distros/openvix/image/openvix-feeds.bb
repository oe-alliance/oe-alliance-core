SUMMARY = "OpenViX Extras"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r5"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    enigma2-plugin-extensions-blurayplayer \
    enigma2-skins-openvix \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-e2-darkos", "", d)} \
    enigma2-plugin-skins-vix-turquoise-hd \
    enigma2-plugin-skins-simple-gray \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-remotecontrolselection \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-systemplugins-terrestrialbouquet \
    oe-alliance-skins \
    openvix-bootlogos-meta \
    openvix-softcams-meta \
    ${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "", "enigma2-display-skins", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    enigma2-plugin-systemplugins-icetv \
    enigma2-plugin-extensions-piconsmissing \
    enigma2-plugin-systemplugins-eitconfig \
    enigma2-plugin-systemplugins-m3uiptv \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-extensions-iptv-org-playlists \
    "
