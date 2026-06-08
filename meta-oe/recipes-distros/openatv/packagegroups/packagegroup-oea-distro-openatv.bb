SUMMARY = "OE-Alliance Distro OpenATV - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash singlecore", "", "packagegroup-oea-network-server", d)} \
    openatv-version-info \
    openatv-bootlogo \
    openatv-spinner \
    enigma-info \
    enigma2-dhcp-wait \
    oe-alliance-picon-feed \
    fstrim-cron \
    chrony \
    dosfstools \
    hdparm \
    smartmontools \
    enigma2-skindefault \
    socketdaemon \
    coreutils-stdbuf \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-softwaremanager \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${OPENATV_EXTENDED}", d)} \
    "

OPENATV_EXTENDED = "\
    dhrystone \
    streambench \
    ntfs-3g \
    unrar \
    tar \
    flip \
    hddtemp \
    rtmpdump \
    zip \
    ofgwrite \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-extensions-enhancedmoviecenter \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "${USBBOOT} mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "e2fsprogs-badblocks", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-extensions-programmlistenupdater", d)} \
    "

USBBOOT = "\
    ${@bb.utils.contains_any("MACHINE", "dm800se dm500hd", "", "enigma2-plugin-extensions-dflash", d)} \
    "

RRECOMMENDS:${PN}:append:bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:dm920 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:dreamone = " enigma2-plugin-systemplugins-amlfrq enigma2-plugin-systemplugins-bluetoothsetup"
RRECOMMENDS:${PN}:append:dreamtwo = " enigma2-plugin-systemplugins-amlfrq enigma2-plugin-systemplugins-bluetoothsetup"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmini4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
