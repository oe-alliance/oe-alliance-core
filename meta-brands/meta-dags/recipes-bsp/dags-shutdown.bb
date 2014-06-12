require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = " \
    file://turnoff_power \
    file://dags-shutdown.sh "

INITSCRIPT_NAME = "dags-shutdown"
INITSCRIPT_PARAMS = "start 89 0 ."

inherit autotools pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/dags-shutdown.sh ${D}/etc/init.d/dags-shutdown
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/turnoff_power ${D}/usr/bin
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
