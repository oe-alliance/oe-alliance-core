inherit upx-compress

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://localoptions.h"

do_configure_prepend() {
    # Apply custom configuration
    cp "${WORKDIR}/localoptions.h" "${B}"
}

do_install_append() {
    echo 'DROPBEAR_EXTRA_ARGS="-R"' > ${WORKDIR}/dropbeardefaults
    echo 'DROPBEAR_RSAKEY_ARGS="-s 2048"' >> ${WORKDIR}/dropbeardefaults
    echo 'DROPBEAR_ECDSAKEY_ARGS="-s 521"' >> ${WORKDIR}/dropbeardefaults
    install -m 0644 ${WORKDIR}/dropbeardefaults  ${D}${sysconfdir}/default/dropbear
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}/etc/init.d/dropbear || true
    fi
}

# add /etc/default/dropbear as configuration file
CONFFILES_${PN} = "/etc/default/dropbear"

