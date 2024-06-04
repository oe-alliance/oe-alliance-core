FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://hwmac"

do_install:append() {
    install -d ${D}${sysconfdir}/network/if-pre-up.d
    install -m 755 ${UNPACKDIR}/hwmac ${D}${sysconfdir}/network/if-pre-up.d/hwmac
}

