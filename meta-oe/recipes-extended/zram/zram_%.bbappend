# Get rid of silly dependencies like util-linux
RDEPENDS:${PN} = ""
# Replace init script with ours, because it depends on bash and assumes
# that zram is a module.
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".1"

do_install:append() {
        # Remove systemd related configuration file
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','false','true',d)}; then
		rm -rf ${D}${systemd_unitdir}/system
        fi
}
