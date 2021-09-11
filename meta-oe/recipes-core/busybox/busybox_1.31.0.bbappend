PR .= ".5"
SRC_URI += " \
            file://mount_single_uuid.patch \
            file://use_ipv6_when_ipv4_unroutable.patch \
            file://telnetd \
            file://inetd \
            file://inetd.conf \
            file://vi.sh \
            file://ntp.script \
            file://0001-Prevent-telnet-connections-from-the-internet-to-the-.patch \
            file://0002-Extended-network-interfaces-support.patch \
            file://0003-Revert-ip-fix-ip-oneline-a.patch \
            file://0004-libbb-make-unicode-printable.patch \
            "

# we do not really depend on mtd-utils, but as mtd-utils replaces 
# include/mtd/* we cannot build in parallel with mtd-utils
DEPENDS += "mtd-utils"

INITSCRIPT_PARAMS_${PN}-mdev = "start 04 S ."

RDEPENDS_${PN} += "odhcp6c"

RRECOMMENDS_${PN} += "${PN}-inetd"
RRECOMMENDS_${PN} += "${PN}-telnetd"

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

do_configure_prepend_sh4 () {
	sed -i 's/^# CONFIG_FEATURE_SWAPON_PRI is not set/CONFIG_FEATURE_SWAPON_PRI=y/g' ${WORKDIR}/defconfig
}

do_install_append() {
    if grep "CONFIG_FEATURE_TELNETD_STANDALONE=y" ${B}/.config; then
	install -m 0755 ${WORKDIR}/telnetd ${D}${sysconfdir}/init.d/telnetd.${BPN}
	sed -i "s:/usr/sbin/:${sbindir}/:" ${D}${sysconfdir}/init.d/telnetd.${BPN}
    fi
    rm -rf ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/vi.sh ${D}${base_bindir}/vi.sh
    install -m 0755 ${WORKDIR}/ntp.script ${D}${sysconfdir}/udhcpc.d/55ntp
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

pkg_postinst_${PN}_append () {
    update-alternatives --install /bin/editor editor /bin/vi.sh 50
}

pkg_postrm_${PN}_append () {
    update-alternatives --remove editor /bin/vi.sh
}

pkg_preinst_${PN}-telnetd_prepend () {
if [ -z "$D" ]; then
    if [ -e $D/etc/inetd.conf ]; then
        grep -vE '^#*\s*(23|telnet)' $D/etc/inetd.conf > $D/tmp/inetd.tmp
        mv $D/tmp/inetd.tmp $D/etc/inetd.conf
    fi
fi
}
