SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r06"

inherit packagegroup
DEPENDS += "oe-alliance-feeds opennfr-3rdparty-plugins enigma2-3rdparty-plugins"

RRECOMMENDS:${PN} = "\
    curlftpfs \
    cdfs \
    tvheadend \
    opennfr-3rdparty-plugins \
    oe-alliance-skins \
    enigma2-display-skins \
    enigma2-skins \
    enigma2-pliplugins \
    enigma2-plugin-extensions-epgimport \
    curlftpfs \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-mainmenu2 \
    enigma2-plugin-skincomponents-bmediacenter-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-ax-blue-skin \
    enigma2-plugin-skincomponents-mediaportal-blue-line-skin \
    enigma2-plugin-skincomponents-mediaportal-smoke-hd-skin \
    enigma2-plugin-skincomponents-skyrecorder-fhd-skin \   
    enigma2-plugin-opennfrskins-ax-blue-fhd-4nfr \
    enigma2-plugin-opennfrskins-blue-line-oct-4nfr \
    enigma2-plugin-opennfrskins-skallihd-fullhd \
    enigma2-plugin-opennfrskins-iflat-blassermod \
    enigma2-plugin-opennfrskins-fenris-blassermod \
    enigma2-plugin-opennfrskins-bundesliga-hd-opennfr-mod \
    enigma2-plugin-opennfrskins-prime-nfrstyle \
    enigma2-plugin-opennfrskins-smoke-light-hd \
    enigma2-plugin-opennfrskins-sphere-fhd-4nfr \
    enigma2-plugin-opennfrskins-multibox-fhd-4nfr \
    enigma2-plugin-opennfrskins-ultimate-hd-4nfr \
    enigma2-plugin-opennfrskins-steampunk-hd-skin-4nfr \
    enigma2-plugin-skins-anadol \
    enigma2-plugin-extensions-vmc \
    enigma2-plugin-extensions-gbipboxclient \
    enigma2-plugin-extensions-chocholousek-picons \
    " 
# remove close cant update to py3
#    enigma2-plugin-extensions-cooltvguide
#    enigma2-plugin-extensions-project-valerie

RRECOMMENDS:${PN}:append_gb800solo = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb7325 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb7358 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb7362 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb73625 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb7356 = "enigma2-plugin-extensions-gbipboxclient"
RRECOMMENDS:${PN}:append_gb7252 = "enigma2-plugin-extensions-gbipboxclient"
