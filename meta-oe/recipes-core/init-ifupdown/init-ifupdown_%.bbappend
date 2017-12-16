FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

RDEPENDS_${PN} += "ifupdown"

do_install_append() {
	install -d ${D}${base_bindir}
	ln -s /bin/false ${D}/bin/init_is_upstart
}
