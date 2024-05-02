SUMMARY = "OpenViX Enigma2"
MAINTAINER = "OpenViX"
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

# required for vix plugin: ffmpeg ofgwrite ${PYTHON_PN}-process libcrypto-compat-0.9.7 ${PYTHON_PN}-compression zip procps bzip2
DEPENDS = "${PYTHON_PN}-process libcrypto-compat-0.9.7 gettext-native"

RDEPENDS:${PN} = "\
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
    enigma2-plugin-systemplugins-vix \
    ${@bb.utils.contains("SMALLBOXWIZARD", "1", "${SMALLBOXWIZARD_IMAGE}", "${NORMAL_IMAGE}", d)} \
    "

SMALLBOXWIZARD_IMAGE = "\
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${NORMAL_IMAGE}", d)} \
"

NORMAL_IMAGE = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-openwebif-webtv \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-vxg \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-openvix-magic-fhd", "", d)} \
    enigma2-plugin-skins-openvix-vix-night-hd \
    enigma2-plugin-skins-openvix-vix-day-hd \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-openvix-youvix-blue" , "", d)} \
    enigma2-plugin-systemplugins-aboutboxbranding \
    enigma2-plugin-systemplugins-opentvzapper \
    enigma2-plugin-systemplugins-xmlupdate \
    ${@bb.utils.contains("MACHINE_FEATURES", "uianimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "osdanimation", "enigma2-plugin-systemplugins-animationsetup" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "quadpip", "enigma2-plugin-systemplugins-quadpip", "", d)} \
    openvix-picon-feed-opkg-conf \
"

RRECOMMENDS:${PN}:append:et8500 = " enigma2-plugin-extensions-yahooweather"
RRECOMMENDS:${PN}:append:tmnanoseplus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmnanosem2 = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmnanosem2plus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmtwin4k = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-extensions-hbbtv-webkit"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-extensions-hbbtv-webkit"
RRECOMMENDS:${PN}:append:osmini4k = " enigma2-plugin-extensions-hbbtv-webkit"
