SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r7"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openview-feeds"

RRECOMMENDS_${PN} = "\
    openview-version-info \
    enigma2-plugin-drivers-usbserial \
    ${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-systemplugins-fastscan \
    ${@base_contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-skinselector \
    ${@base_contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-pli-softcamsetup \
    "

RRECOMMENDS_${PN}_append_gb800se = " swap-workaround"
RRECOMMENDS_${PN}_append_gb800ue = " swap-workaround"
RRECOMMENDS_${PN}_append_gb800solo = " swap-workaround"
RRECOMMENDS_${PN}_append_vusolo2 = " enigma2-plugin-extensions-hbbtv"
RRECOMMENDS_${PN}_append_vuduo2 = " enigma2-plugin-extensions-hbbtv"