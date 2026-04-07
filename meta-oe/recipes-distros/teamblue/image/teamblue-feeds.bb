SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "r9"

inherit packagegroup

DEPENDS += "oe-alliance-feeds"

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "nogui", "packagegroup-teamblue-nogui", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "packagegroup-teamblue-small", "", d)} \
    astra-sm \
    curlftpfs-ng \
    dvblast \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-pluto \
    enigma2-plugin-skins-gb-fhd \
    enigma2-plugin-skins-gbuniverse \
    enigma2-plugin-skins-pax \
    enigma2-plugin-skins-pax-fhd \
    enigma2-plugin-skins-pli-hd \
    enigma2-plugin-skins-teambluehd \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-bitrateviewer \
    enigma2-plugin-extensions-ardmediathek \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-birthdayreminder \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-fritzcall \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-netzkino \
    enigma2-plugin-extensions-shoutcast \
    enigma2-plugin-extensions-simplerss \
    enigma2-plugin-extensions-srfmediathek \
    enigma2-plugin-extensions-sudoku \
    enigma2-plugin-extensions-tetris \
    enigma2-plugin-extensions-werbezapper \
    enigma2-plugin-extensions-vierg \
    enigma2-plugin-extensions-weatherplugin \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-audiorestart \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-epgbackup \
    enigma2-plugin-extensions-epgexport \
    enigma2-plugin-extensions-esame \
    enigma2-plugin-systemplugins-aspectratioswitch \
    enigma2-plugin-systemplugins-automaticcleanup \
    enigma2-plugin-systemplugins-automatictimerlistcleanup \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-mphelp \
    enigma2-plugin-systemplugins-toolkit \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-ftpbrowser \
    enigma2-plugin-extensions-spdyn \
    enigma2-plugin-extensions-infobartunerstate \
    enigma2-plugin-extensions-logomanager \
    enigma2-plugin-extensions-mediadownloader \
    enigma2-plugin-extensions-movietagger \
    enigma2-plugin-extensions-namezap \
    enigma2-plugin-extensions-permanentclock \
    enigma2-plugin-extensions-multirc \
    enigma2-plugin-extensions-reconstructapsc \
    enigma2-plugin-extensions-remotetimer \
    enigma2-plugin-extensions-dvdbackup \
    enigma2-plugin-extensions-series2folder \
    enigma2-plugin-skincomponents-weathercomponent \
    enigma2-plugin-extensions-zapstatistic \
    enigma2-plugin-systemplugins-weathercomponenthandler \
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
