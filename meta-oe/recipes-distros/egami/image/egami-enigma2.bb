SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r3"

inherit packagegroup

DEPENDS = "enigma2-pliplugins egami-feeds"

RRECOMMENDS_${PN} = "\
    enigma2-skindefault \
    enigma2-plugin-skins-egmega32 \
    enigma2-plugin-skins-odreamyfhd \
    \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-egamipermanentclock \
    enigma2-plugin-extensions-egaminews \
    enigma2-plugin-extensions-egamiclearmem \
    enigma2-plugin-extensions-epgimport \
    ${@bb.utils.contains("MACHINE_BRAND", "WeTeK", "", "enigma2-plugin-extensions-egamiboot", d)} \
    \
    enigma2-plugin-extensions-weatherplugin enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent \
    \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "nextv-hbbtv-browser", " enigma2-plugin-extensions-hbbtv-nextv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-openwebif-themes", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "enigma2-plugin-extensions-openwebif-webtv", "enigma2-plugin-extensions-openwebif-vxg", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-egamipluginspeedup \
    enigma2-plugin-systemplugins-videotune \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    \
    ${@bb.utils.contains("MACHINE_BRAND", "Sezam", "${SEZAM_PLUGINS}" , "", d)} \
"

SEZAM_PLUGINS = "\
    enigma2-plugin-systemplugins-3gmodemmanager \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-extensions-youtube \
"

RRECOMMENDS_${PN}_append_wetekplay = " enigma2-plugin-systemplugins-wirelesslan"
RRECOMMENDS_${PN}_append_wetekplay2 = " enigma2-plugin-systemplugins-wirelesslan"
RRECOMMENDS_${PN}_append_bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_dm900 = " enigma2-plugin-systemplugins-fsblupdater"