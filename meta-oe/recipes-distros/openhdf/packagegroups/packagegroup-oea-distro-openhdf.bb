SUMMARY = "OE-Alliance Distro openHDF - branding, skin, and distro-specific plugins"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    ${@bb.utils.contains_any("MACHINE_FEATURES", "smallflash singlecore", "", "packagegroup-oea-network-server", d)} \
    openhdf-version-info \
    openhdf-bootlogo \
    openhdf-spinner \
    openhdf-radio-feed \
    enigma-info \
    enigma2-skindefault \
    ca-certificates \
    curl \
    hddtemp \
    ofgwrite \
    rtmpdump \
    unrar \
    zip \
    tar \
    coreutils \
    python3-mutagen \
    python3-plistlib \
    python3-netifaces \
    python3-compression \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${OPENHDF_EXTENDED}", d)} \
    "

OPENHDF_EXTENDED = "\
    ntfs-3g \
    enigma2-plugin-extensions-openwebif-themes \
    enigma2-plugin-extensions-openwebif-webtv \
    enigma2-plugin-extensions-openwebif-vxg \
    enigma2-plugin-extensions-openwebif-terminal \
    exteplayer3 \
    gstplayer \
    ffmpeg \
    "

RRECOMMENDS:${PN} = "\
    enigma2-plugin-skins-xionhdf \
    enigma2-plugin-extensions-autotimer \
    enigma2-plugin-extensions-epgsearch \
    enigma2-plugin-extensions-imdb \
    enigma2-plugin-extensions-hdftoolbox \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-systemplugins-devicemanager \
    enigma2-plugin-systemplugins-videotune \
    enigma2-plugin-extensions-moviecut \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-systemplugins-softwaremanager \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-systemplugins-positionersetup \
    enigma2-plugin-extensions-audiosync \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "enigma2-plugin-drivers-usbserial", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv1", "enigma2-plugin-extensions-dflash mtd-utils-jffs2", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "dreamboxv2", "e2fsprogs-badblocks", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "webkithbbtv", "enigma2-plugin-extensions-webkithbbtv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "grautec", "enigma2-plugin-extensions-grautec", "", d)} \
    "

RRECOMMENDS:${PN}:append:dags7335 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:dags7356 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:dags7362 = " enigma2-plugin-systemplugins-osd3dsetup"
RRECOMMENDS:${PN}:append:bre2zet2c = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze4k = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:bre2ze = " enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:dm900 = " enigma2-plugin-systemplugins-fsblupdater"
RRECOMMENDS:${PN}:append:osmio4k = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient"
RRECOMMENDS:${PN}:append:osmio4kplus = " enigma2-plugin-extensions-hbbtv-webkit enigma2-plugin-systemplugins-satipclient enigma2-plugin-extensions-simpleumount"
