require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuultimo4k)$"

PV="16.1"
SRCDATE = "20161108"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install_append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "63cfafdbe221e9c87c18abcab7d7ce46"
SRC_URI[sha256sum] = "c7eedb2d82e800b9382ab970027b071387c4281174bdb6e6717ee733ce893787"
