SUMMARY = "Manage skins for OE-Alliance feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
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
	enigma2-plugin-skins-kravenfhd \
	enigma2-plugin-skins-kravenvb \
	enigma2-plugin-skins-sevenhd \
	enigma2-plugin-skins-pli-hd-fullnight \
	"
