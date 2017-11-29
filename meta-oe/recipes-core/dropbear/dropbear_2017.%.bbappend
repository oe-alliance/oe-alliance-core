# create /etc/default/dropbear
# Speedup boot by reducing the host key size. The time it takes grows
# exponentially by key size, the default is 2k which takes several
# seconds on most boxes.

do_install_append() {
    echo 'DROPBEAR_EXTRA_ARGS="-B"' > ${WORKDIR}/dropbeardefaults
    echo 'DROPBEAR_RSAKEY_ARGS="-s 1024"' >> ${WORKDIR}/dropbeardefaults
    install -m 0644 ${WORKDIR}/dropbeardefaults  ${D}${sysconfdir}/default/dropbear
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}/etc/init.d/dropbear || true
    fi
}

# add /etc/default/dropbear as configuration file
CONFFILES_${PN} = "/etc/default/dropbear"

