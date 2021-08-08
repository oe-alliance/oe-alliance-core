FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/${MACHINE}:"

SRC_URI:append:sh4 = " \
     file://fw_env.config \
"

do_install:append:sh4() {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/fw_env.config  ${D}${sysconfdir}
}
