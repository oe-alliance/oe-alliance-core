PRINC = "15"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI += " \
	file://67_init_hddown.dpatch \
	file://92_sata-hddown.dpatch \
	${@base_contains("MACHINE_FEATURES", "gbprogress", "file://proc_progressgb.patch", "file://proc_progress.patch", d)}"

do_install_append() {
        rm ${D}${sysconfdir}/rc*.d/*bootlogd
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
