SUMMARY = "OpenBH Extras"
MAINTAINER = "OpenBH"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r2"

inherit packagegroup

RRECOMMENDS_${PN} = "\
    cdfs \
    curlftpfs \
    enigma2-display-skins \
    enigma2-pliplugins \
    enigma2-plugin-extensions-blurayplayer \
    enigma2-plugin-extensions-epgimport \
    enigma2-plugin-systemplugins-crossepg \
    enigma2-skins \
    enigma2-skins-openvix \
    oe-alliance-skins \
    openbh-picons-meta \
    openssl-old \
    ${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    "
