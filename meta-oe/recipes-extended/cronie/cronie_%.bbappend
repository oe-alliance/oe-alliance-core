
FILESEXTRAPATHS_prepend := "${THISDIR}/cronie:"

CONFFILES_${PN} += "/etc/cron.deny /etc/crontab /etc/default/crond"

# Fix Redhat path to Debian
EXTRA_OECONF_prepend = "SPOOL_DIR=${localstatedir}/spool/cron/crontabs"

do_install_append() {
	# Delete RedHat crap
	rm -rf ${D}${sysconfdir}/sysconfig 2>/dev/null || true

	# Create proper/Debian style paths
	install -d ${D}${sysconfdir}/default/
	install -m 0644 ${S}/crond.sysconfig ${D}${sysconfdir}/default/crond

	# Remove /var/spool/cron as it respectively a link to /etc/cron is part of tuxbox-links
	rm -rf ${D}${localstatedir}/spool/cron 2>/dev/null || true

	# Only install systemd service if we enable systemd
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		# install systemd unit files
		install -d ${D}${systemd_unitdir}/system
		install -m 0644 ${WORKDIR}/crond.service ${D}${systemd_unitdir}/system
		sed -i -e 's,@BASE_BINDIR@,${base_bindir},g' \
			-e 's,@SBINDIR@,${sbindir},g' \
			${D}${systemd_unitdir}/system/crond.service
		# Fix Redhat path to Debian
		sed -e 's#/etc/sysconfig/crond#/etc/default/crond#' -i ${D}${systemd_unitdir}/system/crond.service
	fi

	# Only install SysVinit scripts while we have SysVinit
	if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
		install -d ${D}${sysconfdir}/init.d/
		install -m 0755 ${WORKDIR}/crond.init ${D}${sysconfdir}/init.d/crond
		# Fix Redhat path to Debian
		sed -e 's#CONFIG=/etc/sysconfig/crond#CONFIG=/etc/default/crond#' -i ${D}${sysconfdir}/init.d/crond
	fi

	# below are necessary for a complete cron environment
	install -d ${D}${sysconfdir}/cron/crontabs
	install -m 0755 ${WORKDIR}/crontab ${D}${sysconfdir}/
	mkdir -p ${D}${sysconfdir}/cron.d
	mkdir -p ${D}${sysconfdir}/cron.hourly
	mkdir -p ${D}${sysconfdir}/cron.daily
	mkdir -p ${D}${sysconfdir}/cron.weekly
	mkdir -p ${D}${sysconfdir}/cron.monthly
	touch ${D}${sysconfdir}/cron.deny

	# below setting is necessary to allow normal user using crontab

	# setgid for crontab binary
	chown root:crontab ${D}${bindir}/crontab
	chmod 2755 ${D}${bindir}/crontab

	# allow 'crontab' group write to /var/spool/cron/crontabs
	chown root:crontab ${D}${sysconfdir}/cron
	chown root:crontab ${D}${sysconfdir}/cron/crontabs
	chmod 770 ${D}${sysconfdir}/cron
	chmod 770 ${D}${sysconfdir}/cron/crontabs

	chmod 600 ${D}${sysconfdir}/crontab
}
