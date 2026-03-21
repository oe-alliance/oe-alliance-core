SUMMARY = "OE-Alliance Distro TeamBlue - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

DEPENDS = "enigma2-pliplugins"

RDEPENDS:${PN} = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash singlecore", "", "packagegroup-oea-network-server", d)} \
    enigma-info \
    teamblue-version-info \
    teamblue-bootlogo \
    teamblue-scripts \
    teamblue-spinner \
    oe-alliance-picon-feed \
    dosfstools \
    hdparm \
    smartmontools \
    socketdaemon \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-softwaremanager \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${TEAMBLUE_EXTENDED}", d)} \
    "

TEAMBLUE_EXTENDED = "\
    ntfs-3g \
    unrar \
    tar \
    ca-certificates \
    flip \
    hddtemp \
    rtmpdump \
    zip \
    ofgwrite \
    enigma2-plugin-drivers-exfat \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-epgrefresh \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-moviemanager \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-zaphistorybrowser \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-gbaspectratioswitch \
    enigma2-plugin-systemplugins-osdpositionsetup \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-servicemp3 \
    enigma2-plugin-systemplugins-videomode \
    enigma2-plugin-systemplugins-videotune \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-extensions-programmlistenupdater", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    "
