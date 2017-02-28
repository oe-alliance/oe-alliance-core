SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r02"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    curlftpfs \
    cdfs \
    opennfr-3rdparty-plugins \
    oe-alliance-skins \
    enigma2-display-skins \
    openatv-picons-meta \
    enigma2-skins \
    enigma2-pliplugins \
    enigma2-plugin-extensions-project-valerie \
    enigma2-plugin-extensions-epgimport \
    curlftpfs \
    enigma2-plugin-extensions-enhancedmoviecenter \
    enigma2-plugin-extensions-cooltvguide \
    enigma2-plugin-extensions-webinterface-nfrmod \
    enigma2-plugin-extensions-bmediacenter \
    enigma2-plugin-extensions-mainmenu2 \
    enigma2-plugin-skins-skallihd-fullhd \
    enigma2-plugin-skins-iflat-blassermod \
    enigma2-plugin-skins-fenris-blassermod \
    enigma2-plugin-skins-prime-nfrstyle \
    enigma2-plugin-skins-smoke-light-hd \
    enigma2-plugin-extensions-vmc \
" 
