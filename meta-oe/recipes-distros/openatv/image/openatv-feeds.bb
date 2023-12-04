SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r56"

inherit packagegroup

#not py3 ready
#    enigma2-plugin-skins-dmcconcinnityhd 


DEPENDS += "oe-alliance-feeds enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    chrony \
    fpclock \
    crontab-clearmen \
    enigma2-pliplugins \
    enigma2-display-skins \
    openatv-skinparts \
    oe-alliance-skins \
    enigma2-skins \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs \
    cdfs \
    tvheadend \
    openssl-old \
    ${@bb.utils.contains_any('TARGET_ARCH', 'arm aarch64', 'sysbench', '', d)} \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-settings-defaultsat \
    enigma2-plugin-extensions-specialjump \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-sdgradio \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-openatvreader \
    enigma2-plugin-extensions-openatvstatus \
    enigma2-plugin-skincomponents-advancedmovieselection-steampunk-skin \
    enigma2-plugin-skincomponents-advancedmovieselection-ultimate-skin \
    enigma2-plugin-skincomponents-bmediacenter-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-blue-line-skin \
    enigma2-plugin-skincomponents-mediaportal-smoke-hd-skin \
    enigma2-plugin-skincomponents-skyrecorder-fhd-skin \
    enigma2-plugin-skins-ax-blue-fhd-4atv \
    enigma2-plugin-skins-blue-line-oe-4atv \
    enigma2-plugin-skins-blueaccents-fhd-4atv \
    enigma2-plugin-skins-multibox-fhd-4atv \
    enigma2-plugin-skins-pli-hd-fullnight-4atv \
    enigma2-plugin-skins-steampunk \
    enigma2-plugin-skins-ultimate-hd-4atv \
    enigma2-plugin-skins-iflatfhd \
    enigma2-plugin-skins-glamouraurafhd-atv \
    enigma2-plugin-skins-madmax-impossible \
    enigma2-plugin-skins-overlayhd \
    enigma2-plugin-skincomponents-mediaportal-atv-metrix-style \
    enigma2-plugin-extensions-chocholousek-picons \
    enigma2-plugin-extensions-oaweather \
    mediainfo \
    ncdu \
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "packagegroup-openatv-nogui", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "packagegroup-openatv-small", "", d)} \
    "

# remove close cant update to py3
#    enigma2-plugin-extensions-cooltvguide
#    enigma2-plugin-extensions-project-valerie

RRECOMMENDS:${PN}:append:gb800solo = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = " enigma2-plugin-extensions-gbipboxclient"
