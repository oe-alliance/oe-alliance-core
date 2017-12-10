RPROVIDES_${PN} += "${BPN}-inittab"
RCONFLICTS_${PN} += "${BPN}-inittab"
RREPLACES_${PN} += "${BPN}-inittab"

RDEPENDS_sysv-rc += "perl"

#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH := "${MACHINE_ARCH}"

do_install_append() {
	rm ${D}${base_bindir}/start_getty >/dev/null 2>&1 || true
	install -d ${D}${sysconfdir} >/dev/null 2>&1 || true
	install -m 0644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab
}



SRC_URI += " \
    file://inittab \
    file://67_init_hddown.dpatch \
    file://92_sata-hddown.dpatch \
"

FILES_${PN}-initscripts_remove = "${localstatedir}${base_libdir}/initscripts"
FILES_${PN}-initscripts_remove = "${localstatedir}${base_libdir}/urandom"
FILES_${PN}-initscripts_remove = "${localstatedir}/log/fsck"
