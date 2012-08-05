PRINC = "1"

SRC_URI += " \
        file://67_init_hddown.dpatch \
        file://92_sata-hddown.dpatch \
        file://proc_progress.patch;apply=no \
"

do_install_append() {
        rm ${D}${sysconfdir}/rc*.d/*bootlogd
        patch -d ${D}${sysconfdir}/init.d -p1 -i ${WORKDIR}/proc_progress.patch
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
