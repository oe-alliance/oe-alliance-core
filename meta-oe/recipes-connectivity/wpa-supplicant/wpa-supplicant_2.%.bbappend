PR .= ".1"

inherit upx-compress

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS += "openssl"

SRC_URI += " \
        file://action_wpa.sh \
        file://functions.sh \
        file://ifupdown.sh \
        file://wpa_action \
        file://wpa_action.8 \
        file://fix-build-openssl102q.patch \
"

do_configure:append() {
        sed -e '/^CONFIG_TLS = gnutls/d' -i wpa_supplicant/.config
        echo "CONFIG_DEBUG_SYSLOG=y" >> wpa_supplicant/.config
}
do_install:append() {
        rm -rf ${D}${sysconfdir}/network/if-*.d

        install -d ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${UNPACKDIR}/action_wpa.sh ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${UNPACKDIR}/functions.sh ${D}${sysconfdir}/wpa_supplicant
        install -m 755 ${UNPACKDIR}/ifupdown.sh ${D}${sysconfdir}/wpa_supplicant

        install -d ${D}${sbindir}
        install -m 755 ${UNPACKDIR}/wpa_action ${D}${sbindir}

        install -d ${D}${mandir}/man8
        install -m 755 ${UNPACKDIR}/wpa_action.8 ${D}${mandir}/man8

        install -d ${D}${sysconfdir}/network/if-down.d
        install -d ${D}${sysconfdir}/network/if-post-down.d
        install -d ${D}${sysconfdir}/network/if-pre-up.d
        install -d ${D}${sysconfdir}/network/if-up.d
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-down.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-post-down.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-pre-up.d/${PN}
        ln -s ../../wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-up.d/${PN}
}
