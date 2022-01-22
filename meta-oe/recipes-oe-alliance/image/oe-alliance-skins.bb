SUMMARY = "Manage skins for OE-Alliance feeds."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY:${PN} = "1"
PACKAGES = "${PN}"

PV = "${IMAGE_VERSION}"
PR = "r6"

inherit packagegroup

DEPENDS = "enigma2"

RDEPENDS:${PN} = "\
	${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-plugin-skins-mynovumhd2", d)} \
	${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-plugin-skins-mynovumhd2black", d)} \
	${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-plugin-skins-novum-hd-slim", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-novum-fhd-light", "", d)} \
	enigma2-plugin-skins-army-moodblue-hd \
	${@bb.utils.contains("DISTRO_NAME", "openvix", "" , "enigma2-plugin-skins-kravenhd", d)} \
	enigma2-plugin-skins-pli-hd-fullnight \
	${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-onyx", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-q", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "enigma2-plugin-skins-kiddac-1080-slyk-r19", "", d)} \
	"
