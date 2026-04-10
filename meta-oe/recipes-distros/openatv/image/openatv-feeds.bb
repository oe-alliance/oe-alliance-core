SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r57"

inherit packagegroup

DEPENDS += "oe-alliance-feeds"

RRECOMMENDS:${PN} = "\
    chrony \
    fpclock \
    crontab-clearmen \
    enigma2-display-skins \
    openatv-skinparts \
    oe-alliance-skins \
    enigma2-skins \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs-ng \
    cdfs \
    tvheadend \
    dnscrypt-proxy \
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
    enigma2-plugin-extensions-skymultiview \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-openatvreader \
    enigma2-plugin-extensions-openatvstatus \
    enigma2-plugin-extensions-moviearchiver \
    enigma2-plugin-skins-ax-blue-fhd-4atv \
    enigma2-plugin-skins-blue-line-oe-4atv \
    enigma2-plugin-skins-blueaccents-fhd-4atv \
    enigma2-plugin-skins-pli-hd-fullnight-4atv \
    enigma2-plugin-skins-gradient-fhd-4atv \
    enigma2-plugin-skins-steampunk \
    enigma2-plugin-skins-iflatfhd \
    enigma2-plugin-skins-glamouraurafhd-atv \
    enigma2-plugin-skins-madmax-impossible \
    enigma2-plugin-skins-overlayhd \
    enigma2-plugin-skincomponents-skincomponents-poster-pli-hd-fullnight \
    enigma2-plugin-extensions-chocholousek-picons \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-systemplugins-m3uiptv \
    enigma2-plugin-extensions-plutotv \
    enigma2-plugin-extensions-bootlogoswitcher \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-tvspielfilm \
    enigma2-plugin-extensions-bitrateviewer \
    enigma2-plugin-extensions-ardmediathek \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-birthdayreminder \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-fritzcall \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-netzkino \
    enigma2-plugin-extensions-schiffe \
    enigma2-plugin-extensions-shoutcast \
    enigma2-plugin-extensions-simplerss \
    enigma2-plugin-extensions-srfmediathek \
    enigma2-plugin-extensions-sudoku \
    enigma2-plugin-extensions-tetris \
    enigma2-plugin-extensions-werbezapper \
    enigma2-plugin-extensions-vierg \
    enigma2-plugin-extensions-weatherplugin \
    enigma2-plugin-extensions-zdfmediathek \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-audiorestart \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-babelzapper \
    enigma2-plugin-extensions-cdinfo \
    enigma2-plugin-extensions-epgbackup \
    enigma2-plugin-extensions-epgexport \
    enigma2-plugin-extensions-esame \
    enigma2-plugin-extensions-partnerbox \
    enigma2-plugin-systemplugins-aspectratioswitch \
    enigma2-plugin-systemplugins-automaticcleanup \
    enigma2-plugin-systemplugins-automatictimerlistcleanup \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-mphelp \
    enigma2-plugin-systemplugins-toolkit \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-fstabeditor \
    enigma2-plugin-extensions-ftpbrowser \
    enigma2-plugin-extensions-spdyn \
    enigma2-plugin-extensions-infobartunerstate \
    enigma2-plugin-extensions-kiddytimer \
    enigma2-plugin-extensions-letterbox \
    enigma2-plugin-extensions-logomanager \
    enigma2-plugin-extensions-mediadownloader \
    enigma2-plugin-extensions-movietagger \
    enigma2-plugin-extensions-namezap \
    enigma2-plugin-extensions-permanentclock \
    enigma2-plugin-extensions-multirc \
    enigma2-plugin-extensions-reconstructapsc \
    enigma2-plugin-extensions-remotetimer \
    enigma2-plugin-extensions-dvdbackup \
    enigma2-plugin-extensions-seriesplugin \
    enigma2-plugin-extensions-series2folder \
    enigma2-plugin-extensions-showclock \
    enigma2-plugin-skincomponents-weathercomponent \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-extensions-zapstatistic \
    enigma2-plugin-systemplugins-weathercomponenthandler \
    enigma2-plugin-extensions-webinterface \
    enigma2-plugin-extensions-webadmin \
    enigma2-plugin-extensions-webbouqueteditor \
    enigma2-plugin-extensions-ushare \
    enigma2-plugin-extensions-mediathekviewweb \
    mediainfo \
    ncdu \
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "packagegroup-openatv-nogui", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "packagegroup-openatv-small", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup", "", d)} \
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
