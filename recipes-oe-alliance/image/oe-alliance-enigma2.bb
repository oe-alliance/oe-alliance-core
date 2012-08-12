DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r6"

inherit task

WIFI_DRIVERS = " \
	${@base_contains("MACHINE_FEATURES", "wifiusblegacy", \
										"rt73 rt3070 rtl8192cu rtl871x", \
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-rt2870 \
	\
	kernel-module-ath9k-htc \
	kernel-module-carl9170 \
	kernel-module-rtl8187 \
	kernel-module-rt2500usb \
	kernel-module-rt2800usb \
	kernel-module-rt73usb \
	kernel-module-r8712u \
	kernel-module-zd1211rw \
	rtl8192cu \
	", d)} \
	\
	firmware-rt73 \
	firmware-zd1211 \
	firmware-rtl8192cu \
	firmware-rtl8712u \
	"

DEPENDS = "enigma2 enigma2-plugins enigma2-oe-alliance-plugins oe-alliance-feeds enigma2-3rdparty-plugins"

RDEPENDS = "\
	oe-alliance-feeds-configs \
	aio-grab \
	enigma2 \
	tuxbox-links \
	tuxbox-common \
	mtd-utils \
	kernel-params \
	"

RRECOMMENDS = "\
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-pictureplayer \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-networkwizard \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-videoenhancement \
	enigma2-plugin-systemplugins-videomode \
	\
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi", "enigma2-plugin-systemplugins-wirelesslan ${WIFI_DRIVERS}", "", d)} \
	${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "ci", "enigma2-plugin-systemplugins-commoninterfaceassignment", "", d)} \
	${@base_contains("MACHINE_FEATURES", "dvd", "enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer", "", d)} \
	${@base_contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
	"
