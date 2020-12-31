require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

PV="16.1"
SRCDATE = "20201228"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install_append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "041176f86d8afc302b8f08fca3ea95e8"
SRC_URI[sha256sum] = "34491a4dc59206bdc03cf9a1f4f372bf4985edef8085404178372a11b21d97dc"
