SRC_URI += " file://hwmac"


do_install_append() {
    install -d ${D}${sysconfdir}/network/if-pre-up.d/
    install -m 0755 ${WORKDIR}/hwmac ${D}${sysconfdir}/network/if-pre-up.d
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
