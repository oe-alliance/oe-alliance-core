SUMMARY = "S3 set eth0 wol"
MAINTAINER = "vuplus team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r3"

SRC_URI = "file://ethwol.sh"

inherit update-rc.d
INITSCRIPT_NAME = "ethwol"
INITSCRIPT_PARAMS = "stop 32 0 ."

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/ethwol.sh ${D}/etc/init.d/ethwol
}

pkg_preinst_${PN}_prepend() {
#!/bin/sh
chmod -x /etc/init.d/ethwol
}

pkg_postinst_${PN}_append() {
#!/bin/sh
chmod 755 /etc/init.d/ethwol
}

pkg_prerm_${PN}() {
#!/bin/sh
exit 0
}
