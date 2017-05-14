require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://driver_fix.sh"

INITSCRIPT_NAME = "driver_fix"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/driver_fix.sh ${D}/etc/init.d/driver_fix
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
