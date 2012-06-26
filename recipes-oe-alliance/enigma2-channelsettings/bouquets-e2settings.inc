require conf/license/license-gplv2.inc

SRC_URI = "http://enigma2.world-of-satellite.com/setting_bouquets/${SETTINGS_FILENAME}.tar.gz"

S = "${WORKDIR}"


INHIBIT_PACKAGE_STRIP = "1"

python do_remove_tarball() {
	import os
	try:
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz.md5", d))
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz", d))
		os.remove(bb.data.expand("${DL_DIR}/${SETTINGS_FILENAME}.tar.gz.done", d))
	except:
		pass
}

addtask remove_tarball before do_fetch

do_install() {
	install -d ${D}/etc/enigma2
	install -m 0644 ${S}/etc/enigma2/bouquets.* ${D}/etc/enigma2/
	install -m 0644 ${S}/etc/enigma2/userbouquet.* ${D}/etc/enigma2/
	install -m 0644 ${S}/etc/enigma2/lamedb ${D}/etc/enigma2/
#	install -d ${D}/etc/tuxbox
#	install -m 0644 ${S}/etc/tuxbox/satellites.xml ${D}/etc/tuxbox/
}

pkg_postinst () {
init 4
sleep 1
init 3
}

PACKAGE_ARCH = "all"
FILES_${PN} = "/"
