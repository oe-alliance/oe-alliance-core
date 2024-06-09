require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

PV="16.1"
SRCDATE = "20230613"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install:append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${UNPACKDIR}/vuplus-wifi-util-${MACHINE}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "c2142cf0b0e64707edea9a120220d4a7"
SRC_URI[sha256sum] = "78c529fa631a8c23d611acebca80372848737fdc118f39eed80ced43cdd6e188"
