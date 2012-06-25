FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

SRC_URI += "file://ushare.conf file://init"

INITSCRIPT_NAME = "ushare"
INITSCRIPT_PARAMS = "defaults 20"

inherit update-rc.d

FILES_${PN} += "${sysconfdir}/ushare.conf "

do_install_append() {
	install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/ushare
	install -D -m 0644 ${WORKDIR}/ushare.conf ${D}${sysconfdir}/ushare.conf
}
