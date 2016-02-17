SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r21"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    oe-alliance-skins \
    enigma2-display-skins \
    openatv-picons-meta \
    enigma2-skins \
    enigma2-skins-openvix \
    enigma2-pliplugins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-xmltvimport \
    enigma2-plugin-systemplugins-crossepg \
    curlftpfs \
    cdfs \
    openssl-old \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-skins-dmcconcinnityhd \
    enigma2-plugin-settings-defaultsat \
    enigma2-plugin-extensions-specialjump \
    enigma2-plugin-skins-pli-hd \
    ${@base_contains("MACHINE_BRAND", "AZBOX", "enigma2-plugin-extensions-azplay enigma2-plugin-extensions-aziptv", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    "

RRECOMMENDS_${PN}_append_gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gbquad = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS_${PN}_append_gbquadplus = "enigma2-plugin-extensions-gbipboxclient"
