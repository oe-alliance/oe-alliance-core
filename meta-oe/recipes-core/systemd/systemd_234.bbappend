do_install_append() {
	rm -fR ${D}${sysconfdir}/systemd/system/getty.target.wants
	rm -fR ${D}/lib/systemd/system/multi-user.target.wants/getty.target
}
