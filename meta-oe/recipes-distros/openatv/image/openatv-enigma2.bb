SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r71"

inherit packagegroup

DEPENDS = "enigma2-pliplugins"

RRECOMMENDS:${PN} = " \
    enigma2-skindefault \
    enigma2-plugin-systemplugins-hotplug \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE}", "${NORMAL_IMAGE}", d)} \
    "

SMALLBOXWIZARD_IMAGE = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE}", d)} \
"

NORMAL_IMAGE = "\
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-systemplugins-videotune \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-extensions-mediaplayer \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "shellinabox", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "enigma2-plugin-extensions-dbackup e2fsprogs-badblocks", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-extensions-programmlistenupdater", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools genisoimage cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fcc", "enigma2-plugin-systemplugins-fastchannelchange", "", d)} \
"


GST_BASE_DVD = "\
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
"

RRECOMMENDS:${PN}:append:bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:dm920 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:dreamone = " enigma2-plugin-systemplugins-amlfrq"
RRECOMMENDS:${PN}:append:dreamtwo = " enigma2-plugin-systemplugins-amlfrq"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmini4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"

