DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r25"

inherit task

DEPENDS = "egami-feeds"

RRECOMMENDS = "\
	${ENIGMA2_PLUGINS} \
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-extensions-audiosync \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbc", "virtual/blindscan-dvbc" , "", d)} \
	"

ENIGMA2_PLUGINS = "\
        enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-foreca \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-inimytube \
	${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-extensions-inihbbtv" , "", d)} \
	enigma2-plugin-extensions-egamipermanentclock \
	enigma2-plugin-extensions-egamiboot \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-crossepg \
	${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-systemplugins-3gmodemmanager" , "", d)} \
	${@base_contains("MACHINE", "ventonhdx", "enigma2-plugin-systemplugins-micomupgrade" , "", d)} \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-extensions-mediaportal \
	enigma2-plugin-systemplugins-videoenhancement \	
"