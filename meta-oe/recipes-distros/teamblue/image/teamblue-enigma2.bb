SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "r7"

inherit packagegroup

RRECOMMENDS:${PN} = "\
    enigma-info \
    teamblue-version-info \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-systemplugins-gbaspectratioswitch \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-servicemp3 \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-swapmanager \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    "

