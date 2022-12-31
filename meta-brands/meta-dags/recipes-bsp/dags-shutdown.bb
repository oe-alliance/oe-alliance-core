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
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/dags-shutdown.sh ${D}/etc/init.d/dags-shutdown
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/turnoff_power ${D}/usr/bin
}

pkg_preinst:${PN}:prepend() {
#!/bin/sh
if [ -z "$D" ]; then
    chmod -x $D/etc/init.d/dags-shutdown
fi
}

pkg_postinst:${PN}:append() {
#!/bin/sh
chmod 755 $D/etc/init.d/dags-shutdown
}

pkg_prerm:${PN}() {
#!/bin/sh
exit 0
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

COMPATIBLE_MACHINE = "^(dags7252|dags7335|dags7356|dags7362|dags72604|dags73625)$"