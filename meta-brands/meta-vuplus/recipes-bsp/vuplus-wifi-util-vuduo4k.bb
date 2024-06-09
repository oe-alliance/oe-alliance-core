require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20230613"
SRCDATE_PR = "r0"

PR="${SRCDATE}.${SRCDATE_PR}.1"

SRC_URI = " \
	https://source.mynonpublic.com/vuplus/release/wifi/vuplus-wifi-util-${MACHINE}-${PV}-${SRCDATE}.${SRCDATE_PR}.tar.gz \
"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install:append() {
	install -d ${D}${sysconfdir}/udev
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "cd8a2d9f1648abe8c767d759f6d29d5a"
SRC_URI[sha256sum] = "2a72e157496037805a5efa6db82a32669b7ed3096716bce9a872c527d7633859"
