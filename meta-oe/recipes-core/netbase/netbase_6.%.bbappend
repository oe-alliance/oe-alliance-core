FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".2"

SRC_URI += "file://hwmac \
            file://udhcpc-clean \
"

do_install:append() {
    install -d ${D}${sysconfdir}/network/if-pre-up.d
    install -m 755 ${UNPACKDIR}/hwmac ${D}${sysconfdir}/network/if-pre-up.d/hwmac
    install -m 755 ${UNPACKDIR}/udhcpc-clean ${D}${sysconfdir}/network/if-pre-up.d/udhcpc-clean
}

