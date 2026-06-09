SUMMARY = "OE-Alliance Feed openDroid - distro-specific feed packages"
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
    enigma2-skins \
    enigma2-display-skins \
    cdfs \
    curlftpfs-ng \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-vhannibal-autosettings \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-skincomponents-bmediacenter-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-blue-line-skin \
    enigma2-plugin-skincomponents-mediaportal-smoke-hd-skin \
    enigma2-plugin-skincomponents-skyrecorder-fhd-skin \
    enigma2-plugin-skins-opd-blue-line \
    enigma2-plugin-skins-multibox-fhd-4opd \
    enigma2-plugin-skins-opd-steampunk \
    enigma2-plugin-skins-Ultimate-hd-Skin-4opd \
    openssl-old \
    enigma2-plugin-skincomponents-skincomponents-poster-pli-hd-fullnight \
    enigma2-plugin-extensions-ardmediathek \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-audiorestart \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-birthdayreminder \
    enigma2-plugin-extensions-bitrateviewer \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-dreamexplorer \
    enigma2-plugin-extensions-dvdbackup \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-epgbackup \
    enigma2-plugin-extensions-epgexport \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-esame \
    enigma2-plugin-extensions-filecommander-orig \
    enigma2-plugin-extensions-fritzcall \
    enigma2-plugin-extensions-ftpbrowser \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-infobartunerstate \
    enigma2-plugin-extensions-iptv-org-playlists \
    enigma2-plugin-extensions-letterbox \
    enigma2-plugin-extensions-logomanager \
    enigma2-plugin-extensions-mediadownloader \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-movieorganisor \
    enigma2-plugin-extensions-movietagger \
    enigma2-plugin-extensions-multirc \
    enigma2-plugin-extensions-namezap \
    enigma2-plugin-extensions-netzkino \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-extensions-permanentclock \
    enigma2-plugin-extensions-piconsmissing \
    enigma2-plugin-extensions-pluto \
    enigma2-plugin-extensions-reconstructapsc \
    enigma2-plugin-extensions-remotetimer \
    enigma2-plugin-extensions-series2folder \
    enigma2-plugin-extensions-shoutcast \
    enigma2-plugin-extensions-simplerss \
    enigma2-plugin-extensions-spdyn \
    enigma2-plugin-extensions-srfmediathek \
    enigma2-plugin-extensions-sudoku \
    enigma2-plugin-extensions-tetris \
    enigma2-plugin-extensions-vierg \
    enigma2-plugin-extensions-weatherplugin \
    enigma2-plugin-extensions-werbezapper \
    enigma2-plugin-extensions-zapstatistic \
    enigma2-plugin-skincomponents-weathercomponent \
    enigma2-plugin-extensions-oaweather \
    enigma2-plugin-systemplugins-m3uiptv \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-novum-fhd-light", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-onyx", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-q", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-r19", "", d)} \
    enigma2-plugin-skins-army-moodblue-hd \
    enigma2-plugin-skins-kravenhd \
    enigma2-plugin-skins-pli-hd-fullnight \
    "

RRECOMMENDS:${PN}:append:gb800solo = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = " enigma2-plugin-extensions-gbipboxclient"
