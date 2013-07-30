DESCRIPTION = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PV = "1.0"
PR = "r5"

inherit task

RRECOMMENDS = "\
	oe-alliance-skins \
	enigma2-skins \
	enigma2-display-skins \
	enigma2-pliplugins \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-extensions-cooltvguide \
	enigma2-plugin-extensions-enhancedmoviecenter \
	${@base_contains("MACHINE_BRAND", "AZBOX", "enigma2-plugin-extensions-azplay enigma2-plugin-extensions-aziptv", "", d)} \
	"
