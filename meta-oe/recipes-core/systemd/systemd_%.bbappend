do_install_append() {
	rm -fR ${D}${sysconfdir}/systemd/system/getty.target.wants
	rm -fR ${D}/lib/systemd/system/multi-user.target.wants/getty.target
	rm -fR ${D}/lib/systemd/system/sysinit.target.wants/systemd-udev-trigger.service
	rm -fR ${D}/lib/systemd/system/sockets.target.wants/systemd-udevd-*
	rm -fR ${D}/lib/systemd/system/sysinit.target.wants/systemd-udevd.service
}
