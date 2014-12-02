PR .= ".4"

SRC_URI_IGNORED = " \
            file://0001-ifupdown-support-post-up-pre-down-hooks.patch \
            file://0002-ifupdown-code-shrink.patch \
            file://0003-ifupdown-remove-interface-from-state_list-if-iface_u.patch \
            file://0004-ifupdown-support-metric-for-static-default-gw.patch \
            file://0005-ifupdown-improve-compatibility-with-Debian.patch \
            file://0006-get_linux_version_code-don-t-fail-on-3.0-foo.patch"

SRC_URI_IGNORED += " \
            file://0001-work-around-linux-ext2_fs.h-breakage.patch \
            file://0002-Create-and-use-our-own-copy-of-linux-ext2_fs.h.patch \
            file://0003-Drop-include-bb_linux_ext2_fs.h-use-existing-e2fspro.patch \
            file://0001-nandwrite-add-OOB-support.patch \
            "

SRC_URI += " \
            file://0007-busybox-1.22.1-iplink.patch \
            file://mount_single_uuid.patch \
            file://mdev-mount.sh \
            file://inetd \
            file://inetd.conf \
            file://0008-make_unicode_printable.patch \
            file://busybox-telnetd.xinetd.in \
            file://busybox-telnetd@.service \
            file://busybox-telnetd.socket \
            "

inherit xinetd

# we do not really depend on mtd-utils, but as mtd-utils replaces 
# include/mtd/* we cannot build in parallel with mtd-utils
DEPENDS += "mtd-utils"

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME_${PN}-inetd = "inetd.${BPN}" 
CONFFILES_${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES_${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"
RDEPENDS_${PN}-inetd += "${PN}"

RRECOMMENDS_${PN} += "${PN}-inetd"

PACKAGES =+ "${PN}-cron"
INITSCRIPT_PACKAGES += "${PN}-cron"
INITSCRIPT_NAME_${PN}-cron = "${BPN}-cron" 
FILES_${PN}-cron = "${sysconfdir}/cron ${sysconfdir}/init.d/${BPN}-cron"
RDEPENDS_${PN}-cron += "${PN}"

do_install_append() {
    if grep -q "CONFIG_CRONTAB=y" ${WORKDIR}/defconfig; then
        install -d ${D}${sysconfdir}/cron/crontabs
    fi
    if grep -q "CONFIG_TELNETD=y" ${B}/.config; then
        install -d ${D}${systemd_unitdir}/system
        ln -sf /dev/null ${D}${systemd_unitdir}/system/busybox-telnetd.service
        install -m644 ${WORKDIR}/busybox-telnetd@.service ${D}${systemd_unitdir}/system
        install -m644 ${WORKDIR}/busybox-telnetd.socket ${D}${systemd_unitdir}/system
    fi
    install -d ${D}${sysconfdir}/mdev
    install -m 0755 ${WORKDIR}/mdev-mount.sh ${D}${sysconfdir}/mdev
}

# force mdev

addtask mdev_preinstall after do_install before do_package

do_mdev_preinstall() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/mdev ${D}${sysconfdir}/init.d/mdev
	install -d ${D}${sysconfdir}/rcS.d
	ln -sf ../init.d/mdev ${D}${sysconfdir}/rcS.d/S03mdev
	install -d ${D}${sysconfdir}/rc2.d
	ln -sf ../init.d/mdev ${D}${sysconfdir}/rc2.d/S03mdev
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

SYSTEMD_PACKAGES += "${PN}"
SYSTEMD_SERVICE_${PN} = "busybox-telnetd.socket"
XINETD_SERVICE_${PN} = "busybox-telnetd"