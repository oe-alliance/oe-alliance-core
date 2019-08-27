SUMMARY = "S3 cold boot"
MAINTAINER = "ini Team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r6"

SRC_URI = "file://coldboot file://coldboot.sh"

inherit update-rc.d
INITSCRIPT_NAME = "coldboot"
INITSCRIPT_PARAMS = "start 30 0 ."

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/init.d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/coldboot.sh ${D}/etc/init.d/coldboot
    install -m 0755 ${WORKDIR}/coldboot ${D}/usr/bin/coldboot
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
if [ -z "$D" ]; then
    chmod -x $D/etc/init.d/coldboot
fi
}
pkg_postinst_${PN}_append() {
#!/bin/sh
chmod 755 $D/etc/init.d/coldboot
}
pkg_prerm_${PN}() {
#!/bin/sh
exit 0
}
