SUMMARY = "Manage skins for OE-Alliance feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r2"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS_${PN} = "\
	enigma2-plugin-skins-mynovumhd2 \
	enigma2-plugin-skins-mynovumhd2black \
	enigma2-plugin-skins-novum-hd-slim \
	enigma2-plugin-skins-novum-fhd-light \
	enigma2-plugin-skins-army-moodblue-hd \
	enigma2-plugin-skins-kravenhd \
	enigma2-plugin-skins-pli-hd-fullnight \
	enigma2-plugin-skins-kiddac-1080-onyx \
	enigma2-plugin-skins-kiddac-1080-slyk-q \
	enigma2-plugin-skins-kiddac-1080-slyk-r19 \
	"
