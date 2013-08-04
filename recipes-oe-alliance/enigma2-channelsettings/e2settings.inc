INHIBIT_PACKAGE_STRIP = "1"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz.md5", d))
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz", d))
	except:
		pass
}

addtask remove_tarball before do_fetch

do_install() {
	install -d ${D}/etc/enigma2
	install -m 0644 ${S}/etc/enigma2/bouquets.* ${D}/etc/enigma2/
	install -m 0644 ${S}/etc/enigma2/userbouquet.* ${D}/etc/enigma2/
	install -m 0644 ${S}/etc/enigma2/lamedb ${D}/etc/enigma2/
}

FILES_${PN} = "/"

inherit allarch
