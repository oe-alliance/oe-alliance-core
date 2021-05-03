require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

PV="16.1"
SRCDATE = "20210428"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install_append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "250fe8edadca5238aba1ac2860f86db0"
SRC_URI[sha256sum] = "6091dbd2368cdf888528e53fd53de8d97db41d41759a6e1f09909937f358d5fc"
