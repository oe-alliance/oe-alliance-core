DEPENDS += "openssl"
PRINC = "2"

FILES_wpa-supplicant-passphrase = "${bindir}/wpa_passphrase"

SRC_URI += " \
        file://action_wpa.sh \
        file://functions.sh \
        file://ifupdown.sh \
        file://wpa_action \
        file://wpa_action.8 \
"

do_configure_append() {
        sed -e '/^CONFIG_TLS = gnutls/d' -i .config
        echo "CONFIG_DEBUG_SYSLOG=y" >> .config
}
do_install_append() {
        rm -rf ${D}${sysconfdir}/network/if-*.d

        install -d ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${WORKDIR}/action_wpa.sh ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${WORKDIR}/functions.sh ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${WORKDIR}/ifupdown.sh ${D}${sysconfdir}/wpa_supplicant

        install -d ${D}${sbindir}
        install -m 755 ${WORKDIR}/wpa_action ${D}${sbindir}

        install -d ${D}${mandir}/man8
        install -m 755 ${WORKDIR}/wpa_action.8 ${D}${mandir}/man8

        install -d ${D}${sysconfdir}/network/if-down.d
        install -d ${D}${sysconfdir}/network/if-post-down.d
        install -d ${D}${sysconfdir}/network/if-pre-up.d
        install -d ${D}${sysconfdir}/network/if-up.d
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-down.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-post-down.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-pre-up.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-up.d/${PN}
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
