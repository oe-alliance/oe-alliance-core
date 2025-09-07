SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "8.5"
PR = "r0"

inherit packagegroup

DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    chrony \
    fpclock \
    enigma2-pliplugins \
    openspa-display-skins \
    openspa-skins \
    oe-alliance-skins \
    enigma2-skins \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs-ng \
    cdfs \
    tvheadend \
    openspa-softcams-meta \
    openssl-old \
    ${@bb.utils.contains_any('TARGET_ARCH', 'arm aarch64', 'sysbench', '', d)} \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-settings-defaultsat \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-sdgradio \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-chocholousek-picons \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-extensions-footonsat-spa \
    enigma2-plugin-extensions-iptosat \
    enigma2-plugin-extensions-mytube-spa \
    enigma2-plugin-extensions-openspanettest-spa \
    enigma2-plugin-extensions-permanentevent-spa \
    enigma2-plugin-extensions-plutotv-spa \
    enigma2-plugin-extensions-sinriconnect \
    enigma2-plugin-extensions-spzcamd \
    enigma2-plugin-extensions-spzremotechannels \
    enigma2-plugin-extensions-tailscale \
    enigma2-plugin-extensions-xtraevent-spa \
    enigma2-plugin-systemplugins-m3uiptv \
    enigma2-plugin-systemplugins-spzaddiptv \
    mediainfo \
    ncdu \
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "packagegroup-openspa-nogui", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "packagegroup-openspa-small", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup", "", d)} \
    "

RRECOMMENDS:${PN}:append:gb800solo = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = " enigma2-plugin-extensions-gbipboxclient"
