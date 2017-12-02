do_install_append() {
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		rm ${D}${sysconfdir}/init.d/avahi-daemon
	fi
}
