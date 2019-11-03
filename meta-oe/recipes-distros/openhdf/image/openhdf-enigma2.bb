SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r89"

inherit packagegroup

DEPENDS = "openhdf-feeds"

RRECOMMENDS_${PN} = " \
    openhdf-version-info \
    enigma2-skindefault \
    python-compression \
    enigma2-plugin-skins-xionhdf \ 
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-hdftoolbox \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-systemplugins-extnumberzap \
    enigma2-plugin-extensions-volume-adjust \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-audiosync \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-drivers-usbserial", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "e2fsprogs-badblocks", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer ${GST_BASE_DVD}", "", d)} \
    "

GST_BASE_DVD = "\
    gstreamer1.0-plugins-bad-videoparsersbad \
    gstreamer1.0-plugins-bad-mpegtsmux \
"

RRECOMMENDS_${PN}_append_dags7335 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags7356 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_dags7362 = "enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS_${PN}_append_bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS_${PN}_append_osmio4k = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS_${PN}_append_osmio4kplus = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
