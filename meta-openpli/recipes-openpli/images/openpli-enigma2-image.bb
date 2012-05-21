require openpli-image.bb

WIFI_DRIVERS = " \
	kernel-module-ath9k-htc \
	kernel-module-carl9170 \
	kernel-module-rtl8187 \
	kernel-module-r8712u \
	kernel-module-rt2500usb \
	kernel-module-rt2800usb \
	kernel-module-rt73usb \
	kernel-module-zd1211rw \
	rtl8192cu \
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-rt2870 \
	firmware-rt73 \
	firmware-rtl8192cu \
	firmware-rtl8712u \
	firmware-zd1211 \
	"

ENIGMA2_PLUGINS = " \
	enigma2-plugin-pli-softcamsetup \
	enigma2-plugin-extensions-audiosync \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-positionersetup \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-systemplugins-satfinder \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-extensions-mediascanner \
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-systemplugins-skinselector \
	enigma2-plugin-extensions-pictureplayer \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-systemplugins-networkbrowser \
	enigma2-plugin-systemplugins-fastscan \
	enigma2-plugin-systemplugins-osdpositionsetup \
	enigma2-plugin-systemplugins-hdmicec \
	enigma2-plugin-systemplugins-hotplug \
	${@base_contains("MACHINE_FEATURES", "dvb-c", "enigma2-plugin-systemplugins-cablescan" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "frontprocessor", "enigma2-plugin-systemplugins-frontprocessorupgrade" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "hdtv", "enigma2-plugin-systemplugins-videomode" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "3dtv", "enigma2-plugin-systemplugins-osd3dsetup" , "", d)} \
	${@base_contains("MACHINE_FEATURES", "wifi", "enigma2-plugin-systemplugins-wirelesslan", "", d)} \
	enigma2-plugin-extensions-ppanel \
	enigma2-plugin-extensions-openwebif \
	enigma2-plugin-extensions-autobackup \
	"

DEPENDS += "enigma2 enigma2-plugins enigma2-pliplugins"

ENIGMA2_OPTIONAL = " \
	enigma2-skins \
	enigma2-plugins \
	enigma2-pliplugins \
	enigma2-plugin-extensions-tuxcom \
	enigma2-plugin-extensions-tuxterm \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-security-firewall \
	enigma2-plugin-extensions-xmltvimport \
	enigma2-plugin-extensions-ambx \
	enigma2-plugin-skins-pli-hd \
	channelsettings-enigma2-meta \
	picons-enigma2-meta \
	softcams-enigma2-meta \
	dvbsnoop \
	mtd-utils \
	dvdfs \
	minidlna \
	nano \
	enigma2-plugin-extensions-et-livestream \
	task-openplugins \
	"

IMAGE_INSTALL += " \
	enigma2 \
	${ENIGMA2_PLUGINS} \
	aio-grab \
	tuxbox-common \
	libavahi-client \
	settings-autorestore \
	${@base_contains("MACHINE_FEATURES", "wifi", "${WIFI_DRIVERS}", "", d)} \
	"

OPTIONAL_PACKAGES += " \
	${ENIGMA2_OPTIONAL} \
	"

export IMAGE_BASENAME = "openpli-enigma2"
