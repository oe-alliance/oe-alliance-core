SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r55"
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
    ${@base_contains("DISTRO_FEATURES", "no-autobouquetsmaker", "" , "enigma2-plugin-systemplugins-autobouquetsmaker", d)} \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-extensions-openwebif \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-systemplugins-networkwizard \
    ${ENIGMA2_LOCALES} \
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
    ${@base_contains("MACHINE_FEATURES", "inibt", "enigma2-plugin-extensions-btdevicesmanager" , "", d)} \
    "

ENIGMA2_LOCALES = "\
    enigma2-locale-ar \
    enigma2-locale-bg \
    enigma2-locale-ca \
    enigma2-locale-cs \
    enigma2-locale-da \
    enigma2-locale-de \
    enigma2-locale-el \
    enigma2-locale-en \
    enigma2-locale-en-gb \
    enigma2-locale-es \
    enigma2-locale-et \
    enigma2-locale-fa \
    enigma2-locale-fi \
    enigma2-locale-fr \
    enigma2-locale-fy \
    enigma2-locale-he \
    enigma2-locale-hr \
    enigma2-locale-hu \
    enigma2-locale-is \
    enigma2-locale-it \
    enigma2-locale-ku \
    enigma2-locale-lt \
    enigma2-locale-lv \
    enigma2-locale-nb \
    enigma2-locale-nl \
    enigma2-locale-no \
    enigma2-locale-pl \
    enigma2-locale-pt \
    enigma2-locale-pt-br \
    enigma2-locale-ro \
    enigma2-locale-ru \
    enigma2-locale-sk \
    enigma2-locale-sl \
    enigma2-locale-sr \
    enigma2-locale-sv \
    enigma2-locale-th \
    enigma2-locale-tr \
    enigma2-locale-uk \
    "