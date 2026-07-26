SUMMARY = "OE-Alliance Distro openDroid - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash singlecore", "", "packagegroup-oea-network-server", d)} \
    opendroid-version-info \
    opendroid-bootlogo \
    opendroid-spinner \
    enigma-info \
    enigma2-skindefault \
    wait-for-dhcp \
    ca-certificates \
    ofgwrite \
    openvpn \
    hddtemp \
    dosfstools \
    rtmpdump \
    unrar \
    python3-beautifulsoup4 \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${OPENDROID_EXTENDED}", d)} \
    "

OPENDROID_EXTENDED = "\
    exteplayer3 \
    ffmpeg \
    gstplayer \
    ntfs-3g \
    zip \
    tar \
    curl \
    "

RRECOMMENDS:${PN} = "\
    socketdaemon \
    enigma2-plugin-skins-opd-steampunk \
    enigma2-plugin-extensions-opdboot \
    enigma2-plugin-extensions-vhannibal-autosettings \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-drivers-usbserial \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-openwebif-webtv \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-graphmultiepg \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediaplayer \
    ${@bb.utils.contains("MACHINE_FEATURES", "videoenhancement", "", "enigma2-plugin-systemplugins-videoenhancement", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-extensions-enhancedmoviecenter", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "chromiumos", "enigma2-plugin-extensions-chromium", "", d)} \
    "

RRECOMMENDS:${PN}:append:dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:dm920 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
RRECOMMENDS:${PN}:append:osmini4k = " enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
