SUMMARY = "OpenViX Extras"
MAINTAINER = "OpenViX"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

PV = "1.0"
PR = "r27"

DEPENDS = "\
    oe-alliance-skins \
    enigma2-display-skins \
    enigma2-skins-openvix \
    openvix-bootlogos-meta \
    ${@base_contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
    ${@base_contains("TARGET_ARCH", "mipsel", "openvix-softcams", "", d)} \
    "
