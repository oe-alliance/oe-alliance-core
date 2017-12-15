do_install_append() {
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifquery
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifquery.8
}
