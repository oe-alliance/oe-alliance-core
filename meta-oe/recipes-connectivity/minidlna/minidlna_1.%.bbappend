FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR:append = ".1"

SRC_URI:append = "\
    file://0001_default_sqlite_caches.diff \
    file://minidlna.conf \
"

do_install:append() {
    if [ -f ${D}${sysconfdir}/minidlna.conf ]; then
        rm ${D}${sysconfdir}/minidlna.conf
    fi
    install -m 644 ${UNPACKDIR}/minidlna.conf ${D}${sysconfdir}/minidlna.conf
}

CONFFILES:${PN} = "${sysconfdir}/minidlna.conf"

PACKAGE_NO_LOCALE = "1"
