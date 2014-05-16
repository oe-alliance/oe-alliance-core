require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://ebox-shutdown.sh"

INITSCRIPT_NAME = "ebox-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit autotools pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/ebox-shutdown.sh ${D}/etc/init.d/ebox-shutdown
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

