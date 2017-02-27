PR .= ".34"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI += " \
            file://mount_single_uuid.patch \
            file://introduce_BUILD_BUG_ON.patch \
            file://more_BUILD_BUG_ON.patch \
            file://add_ip_neigh.patch \
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

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME_${PN}-inetd = "inetd.${BPN}" 
CONFFILES_${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES_${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"
RDEPENDS_${PN}-inetd += "${PN}"
PROVIDES += "virtual/inetd"
RPROVIDES_${PN}-inetd += "virtual/inetd"
RCONFLICTS_${PN}-inetd += "xinetd"

PACKAGES =+ "${PN}-telnetd"
INITSCRIPT_PACKAGES += "${PN}-telnetd"
INITSCRIPT_NAME_${PN}-telnetd = "telnetd.${BPN}" 
FILES_${PN}-telnetd = "${sysconfdir}/init.d/telnetd.${BPN}"
RDEPENDS_${PN}-telnetd += "${PN}"
PROVIDES += "virtual/telnetd"
RPROVIDES_${PN}-telnetd += "virtual/telnetd"

RRECOMMENDS_${PN} += "${PN}-inetd"
RRECOMMENDS_${PN} += "${PN}-telnetd"

PACKAGES =+ "${PN}-cron"
INITSCRIPT_PACKAGES += "${PN}-cron"
INITSCRIPT_NAME_${PN}-cron = "${BPN}-cron"
INITSCRIPT_PARAMS_${PN}-mdev = "start 04 S ."
FILES_${PN}-cron = "${sysconfdir}/cron ${sysconfdir}/init.d/${BPN}-cron"
RDEPENDS_${PN}-cron += "${PN}"


do_install_append() {
    if grep -q "CONFIG_CRONTAB=y" ${WORKDIR}/defconfig; then
        install -d ${D}${sysconfdir}/cron/crontabs
    fi
    if grep "CONFIG_FEATURE_TELNETD_STANDALONE=y" ${B}/.config; then
	install -m 0755 ${WORKDIR}/telnetd ${D}${sysconfdir}/init.d/telnetd.${BPN}
	sed -i "s:/usr/sbin/:${sbindir}/:" ${D}${sysconfdir}/init.d/telnetd.${BPN}
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
