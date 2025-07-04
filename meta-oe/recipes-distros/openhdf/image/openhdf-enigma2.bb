SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r93"

inherit packagegroup

RRECOMMENDS:${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "e2fsprogs-badblocks", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools genisoimage cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-drivers-usbserial", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${PYTHON_PN}-compression \
    enigma-info \
    enigma2-plugin-drivers-exfat \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-extensions-audiosync \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-hdftoolbox \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-videotune \
    enigma2-skindefault \
    openhdf-version-info \
    "

GST_BASE_DVD = "\
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
"

RRECOMMENDS:${PN}:append:dags7335 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:dags7356 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:dags7362 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
