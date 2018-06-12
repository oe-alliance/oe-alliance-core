SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r38"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    oe-alliance-skins \
    enigma2-display-skins \
    openatv-picons-meta \
    enigma2-skins \
    enigma2-pliplugins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs \
    cdfs \
    openssl-old \
    enigma2-plugin-extensions-sdgradio \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-skins-dmcconcinnityhd \
    enigma2-plugin-skins-iflatfhd \
    enigma2-plugin-skins-ax-blue-fhd-4atv \
    enigma2-plugin-skins-blue-line-oe-4atv \
    enigma2-plugin-skins-anadol \
    enigma2-plugin-skins-glamour-aura-fhd-atv \
    enigma2-plugin-skincomponents-bmediacenter-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-blue-line-skin \
    enigma2-plugin-skincomponents-mediaportal-atv-metrix-style \
    enigma2-plugin-skincomponents-mediaportal-smoke-hd-skin \
    enigma2-plugin-skincomponents-skyrecorder-fhd-skin \
    enigma2-plugin-settings-defaultsat \
    enigma2-plugin-extensions-specialjump \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-systemplugins-autobouquetsmaker \
    ${@bb.utils.contains("MACHINE_BRAND", "AZBOX", "enigma2-plugin-extensions-azplay enigma2-plugin-extensions-aziptv", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    "

RRECOMMENDS_${PN}_append_gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb73625 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7356 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7252 = "enigma2-plugin-extensions-gbipboxclient"
