SUMMARY = "OE-Alliance Distro OpenViX - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

DEPENDS = "openvix-version-info python3-process libcrypto-compat-0.9.7 gettext-native"

RCONFLICTS:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

RDEPENDS:${PN} = "\
    openvix-version-info \
    openvix-bootlogo \
    openvix-spinner \
    enigma-info \
    enigma2-skindefault \
    fstrim-cron-vix \
    ntpd-sync \
    vsftpd \
    mtd-utils \
    mtd-utils-ubifs \
    ofgwrite \
    oe-alliance-picon-feed \
    "

RRECOMMENDS:${PN} = "\
    ${E2DEFAULTSKIN} \
    enigma2-plugin-systemplugins-vix \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${OPENVIX_EXTENDED}", d)} \
    "

OPENVIX_EXTENDED = "\
    ca-certificates \
    rtmpdump \
    zip \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-openwebif-webtv \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-systemplugins-opentvzapper \
    enigma2-plugin-systemplugins-skinconfig \
    enigma2-plugin-systemplugins-xmlupdate \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-exfat \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    "

RRECOMMENDS:${PN}:append:tmnanoseplus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmnanosem2 = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmnanosem2plus = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:tmtwin4k = " enigma2-plugin-systemplugins-tempfancontrol"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-extensions-hbbtv-webkit"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-extensions-hbbtv-webkit"
RRECOMMENDS:${PN}:append:osmini4k = " enigma2-plugin-extensions-hbbtv-webkit"
