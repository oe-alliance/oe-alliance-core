SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r53"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openhdf-feeds openhdf-3rdparty-plugins"

RRECOMMENDS_${PN} = "\
    openhdf-version-info \
    enigma2-skindefault \
    python-compression \
    enigma2-plugin-skins-nobile \
    enigma2-plugin-skins-army-mod \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-et-portal \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-hdftoolbox \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-virtualzapmod \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-pli-softcamsetup \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-videoenhancement \
    ${@base_contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-cooltvguide \
    ", d)} \
    "

RRECOMMENDS_${PN}_append_gb800solo = ""
RRECOMMENDS_${PN}_append_gb800se = ""
RRECOMMENDS_${PN}_append_gb800ue = ""
RRECOMMENDS_${PN}_append_gbquad = ""
RRECOMMENDS_${PN}_append_dags1 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags2 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags3 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_ixussone = ""
RRECOMMENDS_${PN}_append_vuduo = "" 
RRECOMMENDS_${PN}_append_vusolo = "" 
RRECOMMENDS_${PN}_append_vusolo2 = "" 
RRECOMMENDS_${PN}_append_et9x00 = "" 
RRECOMMENDS_${PN}_append_et6x00 = "" 
RRECOMMENDS_${PN}_append_et5x00 = "" 
RRECOMMENDS_${PN}_append_et4x00 = "" 
