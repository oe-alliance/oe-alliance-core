PRINC = "1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://ntpdate.initd"

INITSCRIPT_NAME = "ntpdate"
INITSCRIPT_PARAMS = "defaults 41"

inherit update-rc.d

do_install_append() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/ntpdate.initd ${D}/${sysconfdir}/init.d/ntpdate
}

FILES_ntpdate += " ${sysconfdir}/init.d/ntpdate"
