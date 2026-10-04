# stcbind.ko is supplied for this machine in the r2 vendor archive.
# The existing driver do_install installs all *.ko, including stcbind.
inherit update-rc.d

FILESEXTRAPATHS:prepend := "${THISDIR}/stcbind:"
SRC_URI += "file://stc_boot"
INITSCRIPT_NAME = "stc_boot"
INITSCRIPT_PARAMS = "start 99 3 . stop 01 0 1 6 ."

do_install:append() {
    install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/default
    install -m 0755 ${S}/stc_boot ${D}${sysconfdir}/init.d/stc_boot
    printf 'STCBIND_TB=0\nSTCBIND_START_DELAY=5\nSTCBIND_UNLOAD_DELAY=70\n' > ${D}${sysconfdir}/default/stcbind
}

FILES:${PN} += "${sysconfdir}/init.d/stc_boot ${sysconfdir}/default/stcbind"
CONFFILES:${PN} += "${sysconfdir}/default/stcbind"

pkg_postinst:${PN}:append() {
    if [ -z "$D" ]; then
        depmod -a ${KV}
    fi
}
