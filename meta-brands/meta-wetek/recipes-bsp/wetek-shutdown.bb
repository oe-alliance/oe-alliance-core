require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI = " file://wetek-shutdown.sh"

S = "${WORKDIR}"

INITSCRIPT_NAME = "wetek-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/wetek-shutdown.sh ${D}/etc/init.d/wetek-shutdown
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
