DESCRIPTION = "Run local boot script (/etc/rc.local)"

require conf/license/license-gplv2.inc

SRC_URI = "file://rc.local.etc \
           file://rc.local.init \
           file://rc-local.service \
           file://debian.conf"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

inherit update-rc.d

INITSCRIPT_NAME = "rc.local"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

SYSTEMD_SERVICE:${PN} = "rc-local.service"
SYSTEMD_AUTO_ENABLE = "enable"

CONFFILES:${PN} = "${sysconfdir}/rc.local"

FILES:${PN} = "${sysconfdir} ${systemd_unitdir}"

do_install () {
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${UNPACKDIR}/rc.local.etc ${D}/${sysconfdir}/rc.local
    install -m 755 ${UNPACKDIR}/rc.local.init ${D}/${sysconfdir}/init.d/rc.local

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -d ${D}${systemd_unitdir}/system/rc-local.service.d
        install -m 0644 ${UNPACKDIR}/rc-local.service ${D}${systemd_unitdir}/system
        ln -s rc-local.service ${D}${systemd_unitdir}/system/rc.local.service
        install -m 0644 ${UNPACKDIR}/debian.conf ${D}${systemd_unitdir}/system/rc-local.service.d
    fi
}
