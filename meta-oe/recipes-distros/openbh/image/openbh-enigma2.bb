SUMMARY = "OpenBh Enigma2"
MAINTAINER = "OpenBh"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r30"

inherit packagegroup

RCONFLICTS:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

# required for obh plugin: ffmpeg ofgwrite ${PYTHON_PN}-process libcrypto-compat-0.9.7 ${PYTHON_PN}-compression zip procps bzip2 mtd-utils mtd-utils-ubifs
# requied for ftp access: vsftpd
DEPENDS = "${PYTHON_PN}-process libcrypto-compat-0.9.7 gettext-native"

RDEPENDS:${PN} = "\
    vsftpd \
    mtd-utils \
    mtd-utils-ubifs \
    ffmpeg \
    ofgwrite \
    ${PYTHON_PN}-process \
    libcrypto-compat-0.9.7 \
    ${PYTHON_PN}-compression \
    zip \
    procps \
    bzip2 \
    enigma2-skindefault \
    enigma-info \
    "

RRECOMMENDS:${PN} = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-obh \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    ${@bb.utils.contains("TARGET_ARCH", "arm", "glibc-compat", "", d)} \
    openbh-picon-feed-opkg-conf \
    "

RRECOMMENDS:${PN}:append:vuduo2 = "enigma2-plugin-extensions-openmultiboot openmultiboot"
RRECOMMENDS:${PN}:append:vusolose = "enigma2-plugin-extensions-openmultiboot openmultiboot"
RRECOMMENDS:${PN}:append:vusolo2 = "enigma2-plugin-extensions-openmultiboot openmultiboot"
RRECOMMENDS:${PN}:append:vuzero = "enigma2-plugin-extensions-openmultiboot openmultiboot"
RRECOMMENDS:${PN}:append:vuultimo = "enigma2-plugin-extensions-openmultiboot openmultiboot"


