DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r30"

inherit packagegroup

DEPENDS = "egami-feeds"

RRECOMMENDS_${PN} = "\
    ${ENIGMA2_PLUGINS} \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-extensions-audiosync \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)} \
    "

ENIGMA2_PLUGINS = "\
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dlnabrowser \
    enigma2-plugin-extensions-accuweather \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-inimytube \
    ${@base_contains("MACHINE", "inihdp", "enigma2-plugin-extensions-inihbbtv" , "", d)} \
    enigma2-plugin-extensions-egamipermanentclock \
    enigma2-plugin-extensions-egamiboot \
    enigma2-plugin-extensions-egamifaq \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-3gmodemmanager \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-extensions-mediaportal \
    enigma2-plugin-systemplugins-videoenhancement \
    enigma2-plugin-skins-egmega32 \
    "