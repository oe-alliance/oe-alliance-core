PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI += " \
            file://mount_single_uuid.patch \
            file://use_ipv6_when_ipv4_unroutable.patch \
            file://mdev-mount.sh \
            file://telnetd \
            file://inetd \
            file://inetd.conf \
            file://vi.sh \
            file://0001-Prevent-telnet-connections-from-the-internet-to-the-.patch \
            file://0002-Extended-network-interfaces-support.patch \
            "

# we do not really depend on mtd-utils, but as mtd-utils replaces 
# include/mtd/* we cannot build in parallel with mtd-utils
DEPENDS += "mtd-utils"

INITSCRIPT_PARAMS_${PN}-mdev = "start 04 S ."

RDEPENDS_${PN} += "odhcp6c"

RRECOMMENDS_${PN} += "${PN}-inetd"
RRECOMMENDS_${PN} += "${PN}-telnetd"

PACKAGES =+ "${PN}-cron"
INITSCRIPT_PACKAGES += "${PN}-cron"
INITSCRIPT_NAME_${PN}-cron = "crond.${BPN}"
CONFFILES_${PN}-cron = "${sysconfdir}/cron"
FILES_${PN}-cron = "${sysconfdir}/cron ${sysconfdir}/init.d/crond.${BPN}"
RDEPENDS_${PN}-cron += "${PN}"
PROVIDES_${PN}-cron += "virtual+cron"
RPROVIDES_${PN}-cron += "virtual+cron"
RCONFLICTS_${PN}-cron += "cronie"

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME_${PN}-inetd = "inetd.${BPN}" 
SYSTEMD_PACKAGES =+ "${PN}-inetd"
SYSTEMD_SERVICE_${PN}-inetd = "busybox-inetd.service"
CONFFILES_${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES_${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"
RDEPENDS_${PN}-inetd += "${PN}"
PROVIDES += "virtual+inetd"
RPROVIDES_${PN}-inetd += "virtual+inetd"
RCONFLICTS_${PN}-inetd += "xinetd"

PACKAGES =+ "${PN}-telnetd"
INITSCRIPT_PACKAGES += "${PN}-telnetd"
INITSCRIPT_NAME_${PN}-telnetd = "telnetd.${BPN}" 
SYSTEMD_PACKAGES =+ "${PN}-telnetd"
SYSTEMD_SERVICE_${PN}-telnetd = "busybox-telnet.service"
FILES_${PN}-telnetd = "${sysconfdir}/init.d/telnetd.${BPN}"
RDEPENDS_${PN}-telnetd += "${PN}"
PROVIDES += "virtual+telnetd"
RPROVIDES_${PN}-telnetd += "virtual+telnetd"

do_install_append() {
    if grep "CONFIG_CROND=y" ${B}/.config; then
       mv ${D}${sysconfdir}/init.d/${BPN}-cron ${D}${sysconfdir}/init.d/crond.${BPN}
    fi
    if grep -q "CONFIG_CRONTAB=y" ${WORKDIR}/defconfig; then
        install -d ${D}${sysconfdir}/cron/crontabs
    fi
    if grep "CONFIG_FEATURE_TELNETD_STANDALONE=y" ${B}/.config; then
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
            install -d ${D}${systemd_unitdir}/system
            sed 's,@sbindir@,${sbindir},g' < ${WORKDIR}/busybox-telnet.service.in \
                > ${D}${systemd_unitdir}/system/busybox-telnet.service
        else
            install -m 0755 ${WORKDIR}/telnetd ${D}${sysconfdir}/init.d/telnetd.${BPN}
            sed -i "s:/usr/sbin/:${sbindir}/:" ${D}${sysconfdir}/init.d/telnetd.${BPN}
        fi
    fi
    if grep "CONFIG_INETD=y" ${B}/.config; then
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
            install -d ${D}${systemd_unitdir}/system
            sed 's,@sbindir@,${sbindir},g' < ${WORKDIR}/busybox-inetd.service.in \
                > ${D}${systemd_unitdir}/system/busybox-inetd.service
            rm ${D}${sysconfdir}/init.d/inetd.${BPN} || true
        fi
    fi
    install -d ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/mdev-mount.sh ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/vi.sh ${D}${base_bindir}/vi.sh
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

pkg_postinst_${PN}_append () {
	update-alternatives --install /bin/editor editor /bin/vi.sh 50
}

pkg_postrm_${PN}_append () {
	update-alternatives --remove editor /bin/vi.sh
}

pkg_preinst_${PN}-telnetd_prepend () {
if [ -e $D/etc/inetd.conf ]; then
	grep -vE '^#*\s*(23|telnet)' $D/etc/inetd.conf > $D/tmp/inetd.tmp
	mv $D/tmp/inetd.tmp $D/etc/inetd.conf
fi
}
