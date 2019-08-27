require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r5"

SRC_URI = " \
    file://turnoff_power \
    file://dags-shutdown.sh "

INITSCRIPT_NAME = "dags-shutdown"
INITSCRIPT_PARAMS = "start 89 0 ."

inherit pkgconfig update-rc.d

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/dags-shutdown.sh ${D}${sysconfdir}/init.d/dags-shutdown
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/turnoff_power ${D}${bindir}
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
if [ -z "$D" ]; then
    chmod -x $D${sysconfdir}/init.d/dags-shutdown
fi
}

pkg_postinst_${PN}_append() {
#!/bin/sh
chmod 755 $D${sysconfdir}/init.d/dags-shutdown
}

pkg_prerm_${PN}() {
#!/bin/sh
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
