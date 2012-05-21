PRINC = "6"

SRC_URI += " \
			file://0001-ifupdown-support-post-up-pre-down-hooks.patch \
			file://0002-ifupdown-code-shrink.patch \
			file://0003-ifupdown-remove-interface-from-state_list-if-iface_u.patch \
			file://0004-ifupdown-support-metric-for-static-default-gw.patch \
			file://0005-ifupdown-improve-compatibility-with-Debian.patch \
			file://0006-get_linux_version_code-don-t-fail-on-3.0-foo.patch"

SRC_URI += " \
			file://0001-work-around-linux-ext2_fs.h-breakage.patch \
			file://0002-Create-and-use-our-own-copy-of-linux-ext2_fs.h.patch \
			file://0003-Drop-include-bb_linux_ext2_fs.h-use-existing-e2fspro.patch \
			"

SRC_URI += " \
			file://mdev-mount.sh \
			file://inetd \
			file://inetd.conf \
			"

PACKAGES =+ "${PN}-inetd"
INITSCRIPT_PACKAGES += "${PN}-inetd"
INITSCRIPT_NAME_${PN}-inetd = "inetd.${BPN}" 
CONFFILES_${PN}-inetd = "${sysconfdir}/inetd.conf"
FILES_${PN}-inetd = "${sysconfdir}/init.d/inetd.${BPN} ${sysconfdir}/inetd.conf"

RRECOMMENDS_${PN} += "${PN}-inetd"

do_install_append() {
	if grep -q "CONFIG_CRONTAB=y" ${WORKDIR}/defconfig; then
		install -d ${D}${sysconfdir}/cron/crontabs
	fi
	install -d ${D}${sysconfdir}/mdev
	install -m 0755 ${WORKDIR}/mdev-mount.sh ${D}${sysconfdir}/mdev
}

FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"
