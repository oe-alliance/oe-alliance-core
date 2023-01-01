SUMMARY = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r3"

DEPENDS = "enigma2 enigma2-locale-meta enigma2-plugins enigma2-oe-alliance-plugins oe-alliance-wifi"

RDEPENDS:${PN} = "\
    enigma2-locale-meta \
    oe-alliance-feeds-configs \
    oe-alliance-botfeed-configs \
    aio-grab \
    enigma2 \
    tuxbox-links \
    tuxbox-common \
    mtd-utils \
    mtd-utils-ubifs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', 'bzip2 pbzip2 rsync', '', d)} \
    ${@bb.utils.contains("PYTHON_PN", "python3", "${PYTHON_PN}-compat2", "", d)} \
    procps \
    parted \
    "

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains("DISTRO_FEATURES", "no-autobouquetsmaker", "" , "enigma2-plugin-systemplugins-autobouquetsmaker", d)} \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-extensions-openwebif \
    ${@bb.utils.contains_any("FLASHSIZE", "64", "", "enigma2-plugin-systemplugins-networkbrowser", d)} \
    enigma2-plugin-systemplugins-networkwizard \
    \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "oe-alliance-drivers", d)} \
    \
    enigma2-plugin-systemplugins-satfinder \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-systemplugins-positionersetup", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbs", "${BLINDSCAN}" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "oe-alliance-wifi", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fastscan", "enigma2-plugin-systemplugins-fastscan" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "ci", "enigma2-plugin-systemplugins-commoninterfaceassignment", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fan", "enigma2-plugin-systemplugins-tempfancontrol", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "transcoding", "enigma2-plugin-systemplugins-transcodingsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "multitranscoding", "enigma2-plugin-systemplugins-multitranscodingsetup", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "inibt", "enigma2-plugin-extensions-btdevicesmanager" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "minitv", "enigma2-plugin-extensions-minitv" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fcc", "enigma2-plugin-systemplugins-fastchannelchange" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "himedia", "enigma2-plugin-systemplugins-servicehisilicon" , "", d)} \
    "

BLINDSCAN = "${@bb.utils.contains_any("FLASHSIZE", "smallflash", "", "enigma2-plugin-systemplugins-blindscan", d)}"