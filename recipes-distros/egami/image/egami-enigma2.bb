DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r51"

inherit task

DEPENDS = "enigma2-pliplugins egami-feeds"

RRECOMMENDS = "\
        enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-inimytube \
	\
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-3gmodemmanager \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-crossepg \
	\
	egami-base-files \
	enigma2-plugin-extensions-egamipermanentclock \
	enigma2-plugin-extensions-egamifaq \
	enigma2-plugin-skins-egmega32 \
	enigma2-plugin-extensions-accuweather \
	enigma2-plugin-extensions-mediaportal \
	${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-extensions-egamiboot" , "", d)} \
	${@base_contains("MACHINE", "mbtwin", "enigma2-plugin-extensions-egamiboot" , "", d)} \
"
