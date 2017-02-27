require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"
PV = "1.0"
PR = "r2"

SRC_URI = "file://${MACHINE}-shutdown.sh"

S = "${WORKDIR}"

INITSCRIPT_NAME = "odroid-shutdown"
INITSCRIPT_PARAMS = "start 39 0 ."

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/${MACHINE}-shutdown.sh ${D}/etc/init.d/odroid-shutdown
}

