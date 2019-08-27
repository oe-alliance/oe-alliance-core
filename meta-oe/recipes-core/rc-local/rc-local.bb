DESCRIPTION = "Run local boot script (/etc/rc.local)"

require conf/license/license-gplv2.inc

SRC_URI = "file://rc.local.etc \
           file://rc.local.init \
           file://rc-local.service \
           file://debian.conf"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "rc.local"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

SYSTEMD_SERVICE_${PN} = "rc-local.service"
SYSTEMD_AUTO_ENABLE = "enable"

CONFFILES_${PN} = "${sysconfdir}/rc.local"

FILES_${PN} = "${sysconfdir} ${systemd_unitdir}"

do_install () {
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${S}/rc.local.etc ${D}/${sysconfdir}/rc.local
    install -m 755 ${S}/rc.local.init ${D}/${sysconfdir}/init.d/rc.local

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -d ${D}${systemd_unitdir}/system/rc-local.service.d
        install -m 0644 ${WORKDIR}/rc-local.service ${D}${systemd_unitdir}/system
        ln -s rc-local.service ${D}${systemd_unitdir}/system/rc.local.service
        install -m 0644 ${WORKDIR}/debian.conf ${D}${systemd_unitdir}/system/rc-local.service.d
    fi
}
