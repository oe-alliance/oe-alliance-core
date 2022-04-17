DESCRIPTION = "OEM-INFO changer for enigma2.info"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "dm520"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"

SRC_URI = "file://oem-info"

inherit update-rc.d

INITSCRIPT_NAME = "oem-info"
INITSCRIPT_PARAMS = "start 7 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/oem-info ${D}${sysconfdir}/init.d/oem-info
}
