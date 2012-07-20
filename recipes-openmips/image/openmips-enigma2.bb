DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r3"

inherit task

#RCONFLICTS_ = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"
#RREPLACES = "enigma2-plugin-extensions-permanenttimeshift enigma2-plugin-systemplugins-skinselector"

DEPENDS = "enigma2-pliplugins openmips-feeds"

RRECOMMENDS = "\
	enigma2-plugin-extensions-ppanel \
	enigma2-plugin-pli-softcamsetup \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-skinselector \
	\
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	"
