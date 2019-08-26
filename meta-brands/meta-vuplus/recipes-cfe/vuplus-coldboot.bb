SUMMARY = "S3 cold boot"
MAINTAINER = "vuplus team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r5"

SRC_URI = "file://coldboot file://coldboot.sh"

inherit update-rc.d
INITSCRIPT_NAME = "coldboot"
INITSCRIPT_PARAMS = "start 50 0 ."

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/init.d ${D}${bindir}
    install -m 0755 ${WORKDIR}/coldboot.sh ${D}${sysconfdir}/init.d/coldboot
    install -m 0755 ${WORKDIR}/coldboot ${D}${bindir}/coldboot
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
if [ -z "$D" ]; then
	chmod -x $D${sysconfdir}/init.d/coldboot
fi
}

pkg_postinst_${PN}_append() {
#!/bin/sh
chmod 755 $D${sysconfdir}/init.d/coldboot
}

pkg_prerm_${PN}() {
#!/bin/sh
exit 0
}
