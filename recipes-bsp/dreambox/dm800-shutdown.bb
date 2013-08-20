require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://dm800-shutdown.sh"

INITSCRIPT_NAME = "dm800-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit autotools pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/dm800-shutdown.sh ${D}/etc/init.d/dm800-shutdown
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

