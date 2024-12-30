DESCRIPTION = "Hotplug E2 Checkinternet"
require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r2"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://checkinternet.sh"

inherit update-rc.d

INITSCRIPT_NAME = "checkinternet.sh"
INITSCRIPT_PARAMS = "start 11 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/checkinternet.sh ${D}${sysconfdir}/init.d/checkinternet.sh
}

INSANE_SKIP = "file-rdeps"
