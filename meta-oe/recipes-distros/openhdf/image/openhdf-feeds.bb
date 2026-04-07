SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r35"

inherit packagegroup

DEPENDS += "oe-alliance-feeds"

RRECOMMENDS:${PN} = "\
    enigma2-skins \
    oe-alliance-skins \
    cdfs \
    enigma2-display-skins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-pluto \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-skins-army-mod \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-extensions-subssupport \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-systemplugins-terrestrialscan \
    enigma2-plugin-skins-nblack51-hdfmod \
    enigma2-plugin-skins-ax-blue-fhd-4hdf \
    enigma2-plugin-skins-blue-line-oct-4hdf \
    enigma2-plugin-skins-multibox-fhd-4hdf \
    enigma2-plugin-skins-steampunk-hd-skin-4hdf \
    enigma2-plugin-skins-ultimate-hd-4hdf \
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
    "

RRECOMMENDS:${PN}:append:gb800solo = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7325 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7358 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7362 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb73625 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7356 = " enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append:gb7252 = " enigma2-plugin-extensions-gbipboxclient"
