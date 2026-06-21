SUMMARY = "OE-Alliance Distro OpenBh - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

DEPENDS = "openbh-version-info python3-process libcrypto-compat-0.9.7 gettext-native"

RCONFLICTS:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES:${PN} = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

RDEPENDS:${PN} = "\
    openbh-version-info \
    openbh-bootlogo \
    openbh-spinner \
    enigma-info \
    enigma2-skindefault \
    blackhole-base \
    blackholesocker \
    fstrim-cron-obh \
    curl \
    dvbsnoop \
    hddtemp \
    inadyn-mt \
    openvpn \
    rtmpdump \
    zip \
    vsftpd \
    mtd-utils \
    mtd-utils-ubifs \
    ffmpeg \
    ofgwrite \
    python3-process \
    python3-compression \
    bzip2 \
    openbh-picon-feed-opkg-conf \
    openbh-extra-feed-opkg-conf \
    ${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "ntfs-3g", d)} \
    "

RRECOMMENDS:${PN} = "\
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-mediaplayer \
    enigma2-plugin-extensions-openbhvip \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-plugin-systemplugins-obh \
    enigma2-plugin-drivers-ntfs-3g \
    enigma2-plugin-drivers-exfat \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    "
