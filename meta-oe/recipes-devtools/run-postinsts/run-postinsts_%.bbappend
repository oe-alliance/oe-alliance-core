FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
do_install_append() {
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		rm -rf ${D}${sysconfdir}/init.d/
	fi
}
