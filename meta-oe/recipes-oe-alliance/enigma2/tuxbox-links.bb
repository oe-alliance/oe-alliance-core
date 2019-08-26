require conf/license/license-gplv2.inc

PN = "tuxbox-links"
PV = "1.0"
PR = "r0"

do_install () {
    install -d ${D}/var
    install -d ${D}/var/spool
    install -d ${D}/usr/keys
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/cron
    install -d ${D}${sysconfdir}/tuxbox/scce
    ln -s /usr/keys ${D}/var/
    ln -s ${bindir} ${D}/var/
    ln -s ${sysconfdir} ${D}/var/
    ln -s ${sysconfdir}/cron ${D}/var/spool/
    ln -s ${sysconfdir}/tuxbox/scce ${D}/var/
}

FILES_${PN} = "/"
