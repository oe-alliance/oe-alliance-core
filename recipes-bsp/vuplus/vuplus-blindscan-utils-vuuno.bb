require vuplus-blindscan-utils.inc

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_blindscan" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_6211_blindscan" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_6222_blindscan" "${D}/${bindir}"
}

SRC_URI[md5sum] = "0cba09223f6382d742d41c814d7b7af6"
SRC_URI[sha256sum] = "9150520fdf3753c5f9c1ccc851a92dcf94beaafd1cd4d5c1b5cab192a581842a"
