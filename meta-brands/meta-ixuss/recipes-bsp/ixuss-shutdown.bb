require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = " \
    file://ixuss-shutdown.sh "

INITSCRIPT_NAME = "ixuss-shutdown"
INITSCRIPT_PARAMS = "start 31 0 ."

inherit autotools pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/ixuss-shutdown.sh ${D}/etc/init.d/ixuss-shutdown
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
