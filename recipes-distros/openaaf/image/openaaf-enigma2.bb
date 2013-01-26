DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r17"

inherit task

DEPENDS = "enigma2-pliplugins openaaf-feeds openaaf-3rdparty-plugins"

RRECOMMENDS = "\
	openaaf-version-info \
	enigma2-plugin-settings-defaultsat \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-infopanel \
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-bmediacenter \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-extensions-volume-adjust \
	enigma2-plugin-systemplugins-autoresolution \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	${@base_contains("MACHINE_FEATURES", "smallflash", "", \
	" \
	enigma2-plugin-extensions-enhancedmoviecenter \
	enigma2-plugin-extensions-cooltvguide \
	", d)} \
	"

RRECOMMENDS_append_et5x00 = " swap-workaround"
RRECOMMENDS_append_vusolo = " swap-workaround"
RRECOMMENDS_append_gb800se = " swap-workaround"
RRECOMMENDS_append_gb800ue = " swap-workaround"
RRECOMMENDS_append_gb800solo = " swap-workaround"
RRECOMMENDS_append_dm800 = " swap-workaround"
RRECOMMENDS_append_dm800se = " swap-workaround"
RRECOMMENDS_append_dm500hd = " swap-workaround"
RRECOMMENDS_append_vusolo2 = " enigma2-plugin-extensions-hbbtv"