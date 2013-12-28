# Get rid of silly dependencies like util-linux
RDEPENDS_${PN} = ""
# Replace init script with ours, because it depends on bash and assumes
# that zram is a module.
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# We don't have systemd
do_install_append() {
	rm -rf ${D}${systemd_unitdir}/system
}
