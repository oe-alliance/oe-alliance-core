SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r49"
PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "enigma2 enigma2-plugins enigma2-oe-alliance-plugins oe-alliance-feeds enigma2-3rdparty-plugins ${@base_contains("MACHINE_FEATURES", "wifi", "oe-alliance-wifi", "", d)}"

RDEPENDS_${PN} = "\
    oe-alliance-feeds-configs \
    aio-grab \
    enigma2 \
    tuxbox-links \
    tuxbox-common \
    mtd-utils \
    ${@base_conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
    procps \
    parted \
    "

RRECOMMENDS_${PN} = "\
    enigma2-plugin-systemplugins-autobouquetsmaker \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-extensions-openwebif \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-systemplugins-networkwizard \
    \
    oe-alliance-drivers \
    \
    ${@base_contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-systemplugins-positionersetup enigma2-plugin-systemplugins-satfinder", d)} \
    ${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "wifi", "oe-alliance-wifi", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "ci", "enigma2-plugin-systemplugins-commoninterfaceassignment", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "dvd", "bdremux replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer gst-plugins-bad-videoparsersbad gst-plugins-bad-mpegtsmux", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "fan", "enigma2-plugin-systemplugins-tempfancontrol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "transcoding", "enigma2-plugin-systemplugins-transcodingsetup", "", d)} \
    "
