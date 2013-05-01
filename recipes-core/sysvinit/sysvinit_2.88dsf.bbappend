PRINC = "2"

SRC_URI += " \
        file://67_init_hddown.dpatch \
        file://92_sata-hddown.dpatch \
        file://proc_progress.patch;apply=no \
		file://proc_progress2.patch;apply=no \
"

do_install_append() {
        rm ${D}${sysconfdir}/rc*.d/*bootlogd
		${@base_contains("MACHINE_FEATURES", "nolcd", "patch -d ${D}${sysconfdir}/init.d -p1 -i ${WORKDIR}/proc_progress2.patch", "patch -d ${D}${sysconfdir}/init.d -p1 -i ${WORKDIR}/proc_progress.patch", d)}
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
