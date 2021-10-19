SUMMARY = "Manage skins for OpenSPA feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "7.5"
PR = "r0"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS_${PN} = "\
	enigma2-plugin-skins-spa24hd \
	enigma2-plugin-skins-metrix-spa \
	"
