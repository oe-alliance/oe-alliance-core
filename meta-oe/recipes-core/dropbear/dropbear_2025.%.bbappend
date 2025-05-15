inherit upx-compress

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " file://localoptions.h \
        ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", "file://add-missing-GRND-NONBLOCK-def.patch", "",d)} \
"

do_configure:prepend() {
    # Apply custom configuration
    cp "${UNPACKDIR}/localoptions.h" "${B}"
}

do_install:append() {
    echo 'DROPBEAR_EXTRA_ARGS="-R"' > ${UNPACKDIR}/dropbeardefaults
    echo 'DROPBEAR_RSAKEY_ARGS="-s 2048"' >> ${UNPACKDIR}/dropbeardefaults
    echo 'DROPBEAR_ECDSAKEY_ARGS="-s 521"' >> ${UNPACKDIR}/dropbeardefaults
    install -m 0644 ${UNPACKDIR}/dropbeardefaults  ${D}${sysconfdir}/default/dropbear
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm ${D}/etc/init.d/dropbear || true
    fi
}

# add /etc/default/dropbear as configuration file
CONFFILES:${PN} = "/etc/default/dropbear"

