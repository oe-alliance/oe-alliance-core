SUMMARY = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r1"

inherit packagegroup

RRECOMMENDS_${PN} = "\
	curlftpfs \
	cdfs \
	\
	enigma2-display-skins \
	openatv-picons-meta \
	egami-bootlogos-meta \
	\
	oe-alliance-skins \
	enigma2-skins \
	enigma2-skins-openvix \
	enigma2-plugin-skins.egami-egarmymoodblue \
	enigma2-plugin-skins.egami-edreamy \
	enigma2-plugin-skins.egami-odreamyfhd \
	enigma2-plugin-skins.egami-a4you \
	enigma2-plugin-skins-egmega32 \
	enigma2-plugin-skins-pli-hd \
	enigma2-plugin-skins-metrix-atv \
	enigma2-plugin-skins-nobile \
	enigma2-plugin-skins-pax \
	enigma2-plugin-skincomponents-egskinscomponents \
	\
	enigma2-plugin-systemplugins-autobouquetsmaker \
	enigma2-plugin-extensions-autobouquets \
	\
	enigma2-plugin-extensions-project-valerie \
	enigma2-plugin-extensions-epgimport \
	enigma2-plugin-extensions-enhancedmoviecenter \
	enigma2-plugin-extensions-cooltvguide \
	enigma2-plugin-extensions-bmediacenter \
	enigma2-plugin-extensions-blurayplayer \
	enigma2-plugin-extensions-openmultiboot openmultiboot \
	enigma2-plugin-extensions-mediaplayer2 \
	\
	python-twisted-web python-gdata python-textutils python-json python-google-api-client python-httplib2 python-youtube-dl-src python-ctypes python-six python-oauth2client python-uritemplate \
	\
	${@bb.utils.contains("MACHINE_FEATURES", "skins1080", "hd-skins-meta", "", d)} \
	\
	libav \
"