PR .= ".3"
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

INITSCRIPT_PARAMS:${PN}-mdev = "start 04 S ."

RDEPENDS:${PN} += "odhcp6c"

RRECOMMENDS:${PN} += "${PN}-inetd"
RRECOMMENDS:${PN} += "${PN}-telnetd"

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME:${PN}-inetd = "inetd.${BPN}" 
CONFFILES:${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES:${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"
RDEPENDS:${PN}-inetd += "${PN}"
PROVIDES += "virtual/inetd"
RPROVIDES:${PN}-inetd += "virtual-inetd"
RCONFLICTS:${PN}-inetd += "xinetd"

PACKAGES =+ "${PN}-telnetd"
INITSCRIPT_PACKAGES += "${PN}-telnetd"
INITSCRIPT_NAME:${PN}-telnetd = "telnetd.${BPN}" 
FILES:${PN}-telnetd = "${sysconfdir}/init.d/telnetd.${BPN}"
RDEPENDS:${PN}-telnetd += "${PN}"
PROVIDES += "virtual/telnetd"
RPROVIDES:${PN}-telnetd += "virtual-telnetd"

do_install:append() {
    if grep "CONFIG_FEATURE_TELNETD_STANDALONE=y" ${B}/.config; then
	install -m 0755 ${WORKDIR}/telnetd ${D}${sysconfdir}/init.d/telnetd.${BPN}
	sed -i "s:/usr/sbin/:${sbindir}/:" ${D}${sysconfdir}/init.d/telnetd.${BPN}
    fi
    rm -rf ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/vi.sh ${D}${base_bindir}/vi.sh
    install -m 0755 ${WORKDIR}/ntp.script ${D}${sysconfdir}/udhcpc.d/55ntp
}

FILESEXTRAPATHS:prepend := "${THISDIR}/${P}:"

pkg_postinst:${PN}:append () {
    update-alternatives --install /bin/editor editor /bin/vi.sh 50
}

pkg_postrm:${PN}:append () {
    update-alternatives --remove editor /bin/vi.sh
}

pkg_preinst:${PN}-telnetd:prepend () {
if [ -z "$D" ]; then
    if [ -e $D/etc/inetd.conf ]; then
        grep -vE '^#*\s*(23|telnet)' $D/etc/inetd.conf > $D/tmp/inetd.tmp
        mv $D/tmp/inetd.tmp $D/etc/inetd.conf
    fi
fi
}
