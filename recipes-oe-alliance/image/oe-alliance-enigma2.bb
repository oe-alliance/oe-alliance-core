DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "1.0"
PR = "r0"

inherit task

WIFI_DRIVERS = " \
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-rtl8192cu \
	firmware-rtl8712u \
	firmware-rt2870 \
	firmware-rt73 \
	firmware-zd1211 \
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
	"

DEPENDS = "enigma2 enigma2-plugins enigma2-pliplugins enigma2-oe-alliance-plugins oe-alliance-feeds"

RDEPENDS = "\
	aio-grab \
	enigma2 \
	tuxbox-links \
	tuxbox-common \
	"

RRECOMMENDS = "\
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-pictureplayer \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-networkwizard \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-videoenhancement \
	enigma2-plugin-systemplugins-videomode \
	\
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	"

ENIGMA2_PLUGINS_append_gb800se = "enigma2-plugin-systemplugins-vfd-giga"
ENIGMA2_PLUGINS_append_gb800ue = "enigma2-plugin-systemplugins-vfd-giga"
ENIGMA2_PLUGINS_append_gb800solo = "enigma2-plugin-systemplugins-vfd-giga"

