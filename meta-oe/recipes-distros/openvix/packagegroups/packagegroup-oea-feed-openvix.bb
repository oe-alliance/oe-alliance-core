SUMMARY = "OE-Alliance Feed OpenViX - distro-specific feed packages"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "r0"

inherit packagegroup

DEPENDS += "packagegroup-oea-feed-core"

RRECOMMENDS:${PN} = "\
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-plutotv-vix \
    enigma2-skins-openvix \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-e2-darkos", "", d)} \
    enigma2-plugin-skins-vix-turquoise-hd \
    enigma2-plugin-skins-simple-gray \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-remotecontrolselection \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-systemplugins-terrestrialbouquet \
    openvix-bootlogos-meta \
    openvix-softcams-meta \
    ${@bb.utils.contains("MACHINE_FEATURES", "nolcd", "", "enigma2-display-skins", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    enigma2-plugin-systemplugins-icetv \
    enigma2-plugin-extensions-piconsmissing \
    enigma2-plugin-systemplugins-eitconfig \
    enigma2-plugin-systemplugins-m3uiptv \
    enigma2-plugin-systemplugins-wanip \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-extensions-iptv-org-playlists \
    enigma2-plugin-systemplugins-aboutboxbranding \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-onyx", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-q", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-r19", "", d)} \
    enigma2-plugin-skins-pli-hd-fullnight \
    "
