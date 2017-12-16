PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI += " \
            file://mount_single_uuid.patch \
            file://use_ipv6_when_ipv4_unroutable.patch \
            file://telnetd \
            file://inetd \
            file://inetd.conf \
            file://busybox-telnet.service.in \
            file://busybox-inetd.service.in \
            file://vi.sh \
            file://0001-Prevent-telnet-connections-from-the-internet-to-the-.patch \
            file://0002-Extended-network-interfaces-support.patch \
            "

# we do not really depend on mtd-utils, but as mtd-utils replaces 
# include/mtd/* we cannot build in parallel with mtd-utils
DEPENDS += "mtd-utils"

DEPENDS_append_class-target = "${@bb.utils.contains('DISTRO_FEATURES','systemd','',' sysvinit update-rc.d insserv',d)}"
PACKAGE_WRITE_DEPS_append = "${@bb.utils.contains('DISTRO_FEATURES','systemd','',' sysvinit update-rc.d insserv',d)}"

RDEPENDS_${PN} += "odhcp6c"

RRECOMMENDS_${PN} += "${PN}-inetd"
RRECOMMENDS_${PN} += "${PN}-telnetd"

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME_${PN}-inetd = "inetd.${BPN}" 
SYSTEMD_PACKAGES =+ "${PN}-inetd"
SYSTEMD_SERVICE_${PN}-inetd = "busybox-inetd.service"
CONFFILES_${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES_${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"
RDEPENDS_${PN}-inetd += "${PN}"
PROVIDES += "virtual/inetd"
RPROVIDES_${PN}-inetd += "inetd"
RCONFLICTS_${PN}-inetd += "xinetd"

PACKAGES =+ "${PN}-telnetd"
INITSCRIPT_PACKAGES += "${PN}-telnetd"
INITSCRIPT_NAME_${PN}-telnetd = "telnetd.${BPN}" 
SYSTEMD_PACKAGES =+ "${PN}-telnetd"
SYSTEMD_SERVICE_${PN}-telnetd = "busybox-telnet.service"
FILES_${PN}-telnetd = "${sysconfdir}/init.d/telnetd.${BPN}"
RDEPENDS_${PN}-telnetd += "${PN}"
PROVIDES += "virtual/telnetd"
RPROVIDES_${PN}-telnetd += "telnetd"

do_install_append() {
    ln -s /sbin/ip ${D}/bin/ip
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
    install -m 0755 ${WORKDIR}/vi.sh ${D}${base_bindir}/vi.sh
    rm -rf ${D}${sysconfdir}/mdev 2>/dev/null || true
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

pkg_postinst_${PN}_append () {
	update-alternatives --install /bin/editor editor /bin/vi.sh 50
}

pkg_postrm_${PN}_append () {
	update-alternatives --remove editor /bin/vi.sh
}
