SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "r8"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "packagegroup-teamblue-nogui", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "packagegroup-teamblue-small", "", d)} \
    astra-sm \
    curlftpfs \
    dvblast \
    enigma2-pliplugins \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-skins-gb-fhd \
    enigma2-plugin-skins-gbuniverse \
    enigma2-plugin-skins-pax \
    enigma2-plugin-skins-pax-fhd \
    enigma2-plugin-skins-pli-hd \
    enigma2-plugin-skins-teambluehd \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-skins \
    fpclock \
    libbluray \
    libudfread \
    enigma2-plugin-systemplugins-m3uiptv \
    mediainfo \
    ncdu \
    oe-alliance-skins \
    openssl-old \
    "
