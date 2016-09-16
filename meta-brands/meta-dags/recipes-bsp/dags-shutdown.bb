require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r5"

RREPLACES_${PN} += "vuplus-shutdown"
RCONFLICTS_${PN} += "vuplus-shutdown"

SRC_URI = " \
    file://turnoff_power \
    file://dags-shutdown.sh "

INITSCRIPT_NAME = "dags-shutdown"
INITSCRIPT_PARAMS = "start 89 0 ."

inherit pkgconfig update-rc.d

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/dags-shutdown.sh ${D}/etc/init.d/dags-shutdown
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/turnoff_power ${D}/usr/bin
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
chmod -x $D/etc/init.d/dags-shutdown
}

pkg_postinst_${PN}_append() {
#!/bin/sh
chmod 755 $D/etc/init.d/dags-shutdown
}

pkg_prerm_${PN}() {
#!/bin/sh
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
