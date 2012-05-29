FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC = "1"

SRC_URI += "file://minidlna.conf file://init"

INITSCRIPT_NAME = "minidlna"
INITSCRIPT_PARAMS = "defaults 20"

inherit update-rc.d

FILES_${PN} += "${sysconfdir}/minidlna.conf "

do_install_append() {
	install -D -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/minidlna
	install -D -m 0644 ${WORKDIR}/minidlna.conf ${D}${sysconfdir}/minidlna.conf
}

