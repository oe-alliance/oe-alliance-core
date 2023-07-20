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
    oe-alliance-branding \
    oe-alliance-remote \
    enigma2 \
    tuxbox-links \
    tuxbox-common \
    bzip2 \
    pbzip2 \
    rsync \
    ${PYTHON_PN}-compat2 \
    ${PYTHON_PN}-twisted-protocols \
    ${PYTHON_PN}-numbers \
    ${PYTHON_PN}-puremagic \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE_DEPEND}", "${NORMAL_IMAGE_DEPEND}", d)} \
    "

SMALLBOXWIZARD_IMAGE_DEPEND = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE_DEPEND}", d)} \
"

NORMAL_IMAGE_DEPEND = "\
    enigma2-locale-meta \
    aio-grab \
"

RRECOMMENDS:${PN} = "\
    enigma2-plugin-systemplugins-networkwizard \
    enigma2-plugin-systemplugins-wirelesslan \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE_RECOMMENDS}", "${NORMAL_IMAGE_RECOMMENDS}", d)} \
    "

SMALLBOXWIZARD_IMAGE_RECOMMENDS = "\
    enigma2-locale-de \ 
    enigma2-locale-en \
    enigma2-locale-fr \
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE_RECOMMENDS}", d)} \
"

NORMAL_IMAGE_RECOMMENDS = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "oe-alliance-drivers", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "no-autobouquetsmaker", "" , "enigma2-plugin-systemplugins-autobouquetsmaker", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-systemplugins-positionersetup", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
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
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    enigma2-plugin-extensions-pictureplayer \
    enigma2-plugin-extensions-openwebif \
    enigma2-plugin-systemplugins-networkbrowser \
    enigma2-plugin-systemplugins-satfinder \
"