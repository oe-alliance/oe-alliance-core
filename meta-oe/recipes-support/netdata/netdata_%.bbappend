FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR:append = ".1"

inherit update-rc.d

SRC_URI += " file://netdata.initd"

FILES:${PN} += "${sysconfdir}"

do_install:append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${UNPACKDIR}/netdata.initd ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

INITSCRIPT_NAME = "${PN}-daemon"
INITSCRIPT_PARAMS = "defaults 60 "