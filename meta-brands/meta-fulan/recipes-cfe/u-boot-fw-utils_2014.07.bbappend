FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/${MACHINE}:"

SRC_URI_sh4 += " \
     file://fw_env.config \
"

do_install_append_sh4() {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/fw_env.config  ${D}${sysconfdir}
}

