FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".3"

SRC_URI += "file://hwmac \
            file://linkspeed \
            file://udhcpc-clean \
"

do_install:append() {
    install -d ${D}${sysconfdir}/network/if-pre-up.d
    install -m 755 ${UNPACKDIR}/hwmac ${D}${sysconfdir}/network/if-pre-up.d/hwmac
    install -m 755 ${UNPACKDIR}/linkspeed ${D}${sysconfdir}/network/if-pre-up.d/linkspeed
    install -m 755 ${UNPACKDIR}/udhcpc-clean ${D}${sysconfdir}/network/if-pre-up.d/udhcpc-clean
}

