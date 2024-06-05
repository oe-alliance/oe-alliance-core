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

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/ethwol.sh ${D}/etc/init.d/ethwol
}

pkg_preinst:${PN}:prepend() {
#!/bin/sh
if [ -z "$D" ]; then
	chmod -x /etc/init.d/ethwol
fi
}

pkg_postinst_ontarget:${PN}:append() {
#!/bin/sh
chmod 755 /etc/init.d/ethwol
}

pkg_prerm:${PN}() {
#!/bin/sh
exit 0
}
