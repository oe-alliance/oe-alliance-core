ALTERNATIVE_PRIORITY = "10"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    chmod 755 ${D}${sysconfdir}/init.d/functions
}
