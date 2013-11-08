DESCRIPTION = "Merge machine and distro options to create a enigma2 feeds machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
PV = "1.0"
PR = "r5"

inherit task

RCONFLICTS_ = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
RREPLACES = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

RRECOMMENDS = "\
	oe-alliance-skins \
	enigma2-skins \
	enigma2-plugin-settings-default-ventonsupport \
	enigma2-plugin-extensions-project-valerie \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-systemplugins-crossepg \
	"

