SUMMARY = "OpenBh Extras"
MAINTAINER = "OpenBh"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r15"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    cdfs \
    curlftpfs \
    enigma2-display-skins \
    enigma2-plugin-skins-e2-darkos \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-iptv-org-playlists \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-remotecontrolselection \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-systemplugins-terrestrialbouquet \
    enigma2-skins-openvix \
    oe-alliance-skins \
    openssl-old \
    openvix-softcams-meta \
    ${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "", "enigma2-display-skins", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    enigma2-plugin-systemplugins-icetv \
    enigma2-plugin-systemplugins-eitconfig \
    enigma2-plugin-systemplugins-m3uiptv \
    enigma2-plugin-extensions-oaweather \
    "
