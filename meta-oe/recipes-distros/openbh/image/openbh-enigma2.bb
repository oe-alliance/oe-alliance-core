SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r54"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openbh-feeds"

RRECOMMENDS_${PN} = " \
    enigma2-skindefault \
    openbh-version-info \
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
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-osd3dsetup \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-extensions-dlnabrowser \
    enigma2-plugin-extensions-dlnaserver \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-extensions-epgsearchpli \
    enigma2-plugin-extensions-epgimportfilter \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
    ${@base_contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "webkithbbtv", "webkit-hbbtv-browser", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "omb", "enigma2-plugin-extensions-openmultiboot", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "omb", "openmultiboot", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "vukodi", "enigma2-plugin-extensions-vuplus-kodi", "", d)} \
    "

RRECOMMENDS_${PN}_append_wetekplay = " enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-systemplugins-wirelesslan"
RRECOMMENDS_${PN}_append_vusolo4k = "enigma2-plugin-skins-metrix-atv"
RRECOMMENDS_${PN}_append_vuduo2 = "enigma2-plugin-skins-metrix-atv"
RRECOMMENDS_${PN}_append_vusolo2 = "enigma2-plugin-skins-metrix-atv"
RRECOMMENDS_${PN}_append_vusolose = "enigma2-plugin-skins-metrix-atv"
RRECOMMENDS_${PN}_append_vuzero = "enigma2-plugin-skins-metrix-atv"
RRECOMMENDS_${PN}_append_vuultimo = "enigma2-plugin-skins-metrix-atv"

