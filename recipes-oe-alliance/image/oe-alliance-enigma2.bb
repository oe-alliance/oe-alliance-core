DESCRIPTION = "Merge machine and distro options to create a enigma2 machine task/package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit task

PV = "1.0"
PR = "r31"
PACKAGE_ARCH = "${MACHINE_ARCH}"

OPTIONAL_WIFIDRIVERS = "${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "enigma2-plugin-drivers-network-usb-rt3070", " \
	enigma2-plugin-drivers-network-usb-ath9k-htc \
	enigma2-plugin-drivers-network-usb-carl9170 \
	enigma2-plugin-drivers-network-usb-rt2800 \
	enigma2-plugin-drivers-network-usb-rt2500 \
	enigma2-plugin-drivers-network-usb-rtl8187 \
	enigma2-plugin-drivers-network-usb-zd1211rw \
	", d)} \
	enigma2-plugin-drivers-network-usb-rt73 \
	enigma2-plugin-drivers-network-usb-r8712u \
	"

WIFI_DRIVERS = " \
	${@base_contains("MACHINE", "vuduo2", "enigma2-plugin-drivers-network-usb-rt5572", "", d)} \
	${@base_contains("MACHINE_BRAND", "Vu+", "enigma2-plugin-drivers-network-usb-rtl8192cu", "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi61", "rt61", "", d)} \
	${@base_contains("DISTRO_NAME", "openvix", "", "${OPTIONAL_WIFIDRIVERS}", d)} \
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
	enigma2-plugin-systemplugins-autobouquetsmaker \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-pictureplayer \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-networkwizard \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-videomode \
	\
	${@base_contains("MACHINE_FEATURES", "blindscan-dvbs", "enigma2-plugin-systemplugins-blindscan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "hdmicec", "enigma2-plugin-systemplugins-hdmicec" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi", "enigma2-plugin-systemplugins-wirelesslan ${WIFI_DRIVERS}", "", d)} \
	${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "ci", "enigma2-plugin-systemplugins-commoninterfaceassignment", "", d)} \
	${@base_contains("MACHINE_FEATURES", "dvd", "replex mjpegtools dvdauthor dvd+rw-tools cdrkit cdfs cdtextinfo enigma2-plugin-extensions-cdinfo enigma2-plugin-extensions-dvdburn enigma2-plugin-extensions-dvdplayer", "", d)} \
	${@base_contains("MACHINE_FEATURES", "fanctrl", "enigma2-plugin-systemplugins-fancontrol", "", d)} \
	${@base_contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
	${@base_contains("MACHINE_FEATURES", "fan", "enigma2-plugin-systemplugins-tempfancontrol", "", d)} \
	${@base_contains("MACHINE_FEATURES", "transcoding", "enigma2-plugin-systemplugins-transcodingsetup", "", d)} \
	\
	${@base_contains("MACHINE", "vuduo2", "enigma2-plugin-extensions-lcd4linux", "", d)} \
	"
