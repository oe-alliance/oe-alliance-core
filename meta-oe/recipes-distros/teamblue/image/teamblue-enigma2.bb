SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "r2"

inherit packagegroup

DEPENDS = "teamblue-feeds"

RRECOMMENDS_${PN} = "\
    teamblue-version-info \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-systemplugins-gbaspectratioswitch \
    enigma2-plugin-systemplugins-autoresolution \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-fastscan \
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-skinselector \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-swapmanager \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    "

