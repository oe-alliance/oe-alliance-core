PR="r1"

SRC_URI_remove = "file://inet-6-.defn-fix-inverted-checks-for-loopback.patch"

do_install_append() {
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifquery
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifquery.8
}
