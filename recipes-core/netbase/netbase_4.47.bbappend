PRINC = "2"

SRC_URI += " file://hwmac"

do_install_append() {
	install -d ${D}${sysconfdir}/network/if-up.d/
	install -m 0755 ${WORKDIR}/hwmac ${D}${sysconfdir}/network/if-up.d
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
