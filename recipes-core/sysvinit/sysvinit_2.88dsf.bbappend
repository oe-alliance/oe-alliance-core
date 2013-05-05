PRINC = "4"

SRC_URI += " \
        file://67_init_hddown.dpatch \
        file://92_sata-hddown.dpatch \
        file://proc_progress.patch;apply=no \
		file://proc_progressgb.patch;apply=no \
"

do_install_append() {
        rm ${D}${sysconfdir}/rc*.d/*bootlogd
		${@base_contains("MACHINE_FEATURES", "gbprogress", "patch -d ${D}${sysconfdir}/init.d -p1 -i ${WORKDIR}/proc_progressgb.patch", "patch -d ${D}${sysconfdir}/init.d -p1 -i ${WORKDIR}/proc_progress.patch", d)}
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
