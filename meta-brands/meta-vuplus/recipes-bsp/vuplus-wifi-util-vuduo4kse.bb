require vuplus-wifi-util.inc

COMPATIBLE_MACHINE = "^(vuduo4kse)$"

PV="17.1"
SRCDATE = "20200909"
SRCDATE_PR = "r0"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 60 S ."
INITSCRIPT_NAME = "vuplus-wifi-init.sh"

do_install_append() {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/${INITSCRIPT_NAME}
}

SRC_URI[md5sum] = "381337f41987e8e5761d2f683641f86d"
SRC_URI[sha256sum] = "ced69322119be2606adb6aba1e5d2c5fcbb76969ef8b75ca20389058e121948c"
