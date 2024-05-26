require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

PV="17.1"
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
	install -m 0755 ${S}/bcmwifi_firmware.sh ${D}${sysconfdir}/udev/
	install -m 0755 ${S}/bcmwifi_drv.sh ${D}${sysconfdir}/udev/
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "fc0f909ad46dfb7f4d2ee73607c3dbbd"
SRC_URI[sha256sum] = "240bfad0e6504d175a4f1266c077da12e9904ab310d92203ac2ff027f4eaeba0"
