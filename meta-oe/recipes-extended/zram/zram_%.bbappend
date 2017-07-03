# Get rid of silly dependencies like util-linux
RDEPENDS_${PN} = ""
# Replace init script with ours, because it depends on bash and assumes
# that zram is a module.
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
        # Remove systemd related configuration file
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','false','true',d)}; then
		rm -rf ${D}${systemd_unitdir}/system
        fi
}
