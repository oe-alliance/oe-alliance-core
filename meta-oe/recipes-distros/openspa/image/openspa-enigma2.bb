SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r11"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openspa-feeds"

RRECOMMENDS_${PN} = " \
    enigma2-skindefault \
    openspa-version-info \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-infopanel \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-tvweb \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediaplayer \
    ${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
    "

RRECOMMENDS_${PN}_append_vusolo2 = "enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo2 = "enigma2-plugin-extensions-hbbtv"
