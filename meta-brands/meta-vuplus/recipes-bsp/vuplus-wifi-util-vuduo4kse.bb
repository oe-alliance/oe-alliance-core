require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

PV="17.1"
SRCDATE = "20201228"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install_append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "df509ddc241fe1cc5b44ed3c58375ad5"
SRC_URI[sha256sum] = "0ed3daa8883f21000b7e2d935e5bf27a13471dbc5df0444baa9fac4328d7f666"
