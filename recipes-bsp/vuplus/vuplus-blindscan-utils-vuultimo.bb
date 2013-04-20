require vuplus-blindscan-utils.inc

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_blindscan" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_6211_blindscan" "${D}/${bindir}"
	install -m 0755 "${S}/vuplus_6222_blindscan" "${D}/${bindir}"
}

SRC_URI[md5sum] = "8e11070a8c7ffa84b271d058b4a52ce5"
SRC_URI[sha256sum] = "3091ba5f7f126d50ea83ea5861a255ceed4a5ce4d4b2edf7a0518c8b01b0a51f"
