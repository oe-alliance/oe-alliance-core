DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r17"

inherit packagegroup

DEPENDS = "enigma2-pliplugins openmips-feeds"

RRECOMMENDS_${PN} = "\
    openmips-version-info \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-ppanel \
    enigma2-plugin-pli-softcamsetup \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-extensions-gbaspectratioswitch \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-skinselector \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-swapmanager \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-systemplugins-videoenhancement \
    enigma2-plugin-drivers-network-usb-smsc75xx \
    enigma2-plugin-drivers-network-usb-ax88179-178a \
    enigma2-plugin-drivers-network-usb-asix \
    enigma2-plugin-drivers-network-usb-rt3573 \
    enigma2-plugin-drivers-network-usb-rt5572 \
    ${@base_contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
    "

RRECOMMENDS_${PN}_append_gbquad = " webbrowser-utils enigma2-plugin-extensions-webbrowser"
RRECOMMENDS_${PN}_append_vusolo2 = " enigma2-plugin-extensions-hbbtv"    
