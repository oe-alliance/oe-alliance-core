SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r58"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openatv-feeds"

RRECOMMENDS_${PN} = " \
    enigma2-skindefault \
    openatv-version-info \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-drivers-usbserial", d)} \
    enigma2-plugin-extensions-infopanel \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-systemplugins-videotune \
    ${@base_contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediaplayer \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-openwebif-themes", d)} \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "enigma2-plugin-extensions-openwebif-webtv", "enigma2-plugin-extensions-openwebif-vxg", d)} \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
    ${@base_contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup e2fsprogs-badblocks", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "webkithbbtv", "webkit-hbbtv-browser", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "nextv-hbbtv-browser", " enigma2-plugin-extensions-hbbtv-nextv", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-extensions-programmlistenupdater", d)} \
    "

RRECOMMENDS_${PN}_append_wetekplay = " enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-systemplugins-wirelesslan"
RRECOMMENDS_${PN}_append_wetekplay2 = " enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-systemplugins-wirelesslan"
RRECOMMENDS_${PN}_append_bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_dm900 = " enigma2-plugin-systemplugins-fsblupdater"
