require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

PV="17.1"
SRCDATE = "20210428"
SRCDATE_PR = "r0"

PR="${SRCDATE}.${SRCDATE_PR}.1"

SRC_URI = " \
	http://code.vuplus.com/download/release/wifi/vuplus-wifi-util-${MACHINE}-${PV}-${SRCDATE}.${SRCDATE_PR}.tar.gz \
"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install:append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${S}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${S}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "1258a3792ed175ca36236ba8f230bba9"
SRC_URI[sha256sum] = "fc5c1ba0ca9bad56c164b3065773f9820693cebc83c02293ae0e6a47e7402fd0"
