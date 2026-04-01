SUMMARY = "OE-Alliance Enigma2 Core - base enigma2 and essential plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

DEPENDS = "enigma2 enigma2-locale-meta enigma2-plugins"

RDEPENDS:${PN} = "\
    packagegroup-base \
    oe-alliance-branding \
    oe-alliance-remote \
    oe-alliance-feeds-configs \
    oe-alliance-3rdparty-feed \
    oe-alliance-botfeed-configs \
    enigma2 \
    tuxbox-links \
    tuxbox-common \
    bzip2 \
    pbzip2 \
    rsync \
    enigma2-plugin-systemplugins-hotplug \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${ENIGMA2_CORE_EXTENDED_RDEPENDS}", d)} \
    "

ENIGMA2_CORE_EXTENDED_RDEPENDS = "\
    aio-grab \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "edid-decode", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "aarch64", "edid-decode", "", d)} \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-systemplugins-satfinder \
    enigma2-plugin-extensions-openwebif \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-systemplugins-networkbrowser \
    autofs \
    enigma2-locale-meta \
    packagegroup-oea-wifi \
    wireless-tools \
    nmap \
    network-usb-drivers-meta \
    ${@bb.utils.contains("MACHINEBUILD", "gbquad", "enigma2-plugin-drivers-network-usb-smsc75xx", "", d)} \
    ${@bb.utils.contains("MACHINEBUILD", "gbquadplus", "enigma2-plugin-drivers-network-usb-smsc75xx", "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "no-autobouquetsmaker", "", "enigma2-plugin-systemplugins-autobouquetsmaker", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-systemplugins-positionersetup", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fastscan", "enigma2-plugin-systemplugins-fastscan", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "ci", "enigma2-plugin-systemplugins-commoninterfaceassignment", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fan", "enigma2-plugin-systemplugins-tempfancontrol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "transcoding", "enigma2-plugin-systemplugins-transcodingsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "multitranscoding", "enigma2-plugin-systemplugins-multitranscodingsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "inibt", "enigma2-plugin-extensions-btdevicesmanager", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "himedia", "enigma2-plugin-systemplugins-servicehisilicon", "", d)} \
    "

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-locale-de", d)} \ 
    enigma2-locale-en \
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-locale-fr", d)} \
    enigma2-plugin-systemplugins-networkwizard \
    enigma2-plugin-systemplugins-wirelesslan \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${ENIGMA2_CORE_EXTENDED_RRECOMMENDS}", d)} \
    "

ENIGMA2_CORE_EXTENDED_RRECOMMENDS = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fcc", "enigma2-plugin-systemplugins-fastchannelchange", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "minitv", "enigma2-plugin-extensions-minitv", "", d)} \
"