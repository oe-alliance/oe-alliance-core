SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "5.3"
PR = "r10"

inherit packagegroup

DEPENDS = "enigma2-pliplugins opendroid-feeds"

RRECOMMENDS_${PN} = "\
    opendroid-version-info \
    enigma2-skindefault \
    python-compression \
    enigma2-plugin-skins-opendroid \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-extrapanel \
    enigma2-plugin-extensions-autosetting \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-systemplugins-videoenhancement \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediaplayer \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dreambox", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    enigma2-plugin-extensions-audiosync \
    ", d)} \
    "

RRECOMMENDS_${PN}_append_gb800solo = ""
RRECOMMENDS_${PN}_append_dags7335 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags7356 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags7362 = "enigma2-plugin-systemplugins-osd3dsetup"
