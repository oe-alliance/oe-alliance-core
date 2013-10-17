DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r13"

inherit task

DEPENDS = "enigma2-pliplugins odinsupport-feeds"

RRECOMMENDS = "\
	enigma2-skindefault \
	odinsupport-version-info \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-infopanel \
	enigma2-plugin-extensions-bmediacenter \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-cooltvguide \
	enigma2-plugin-extensions-enhancedmoviecenter \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-extensions-autobackup \
	enigma2-plugin-systemplugins-videomode \
	enigma2-plugin-systemplugins-autoresolution \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-videotune \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "boxmodel", "boxmodel", "", d)} \
	"
