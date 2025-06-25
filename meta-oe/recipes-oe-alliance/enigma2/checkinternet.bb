DESCRIPTION = "Hotplug E2 Checkinternet"
require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r3"

S = "${UNPACKDIR}"

SRC_URI = "file://checkinternet"

inherit update-rc.d

INITSCRIPT_NAME = "checkinternet"
INITSCRIPT_PARAMS = "start 90 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/checkinternet ${D}${sysconfdir}/init.d/checkinternet
}

INSANE_SKIP = "file-rdeps"
