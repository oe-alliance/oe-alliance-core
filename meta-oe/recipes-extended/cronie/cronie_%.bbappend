
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PROVIDES += "virtual/cron"
RCONFLICTS_${PN} += "busybox-cron"

INITSCRIPT_NAME = "cron"
SYSTEMD_SERVICE_${PN} = "cron.service"

CONFFILES_${PN} += "/etc/cron.deny /etc/crontab /etc/default/crond"

# Fix Redhat path to Debian
EXTRA_OECONF_prepend = "SPOOL_DIR=${localstatedir}/spool/cron/crontabs"

do_install_append() {
	# Delete RedHat crap
	rm -rf ${D}${sysconfdir}/sysconfig 2>/dev/null || true
	rm ${D}${systemd_unitdir}/system/crond.service 2>/dev/null || true
	rm ${D}${sysconfdir}/init.d/crond 2>/dev/null || true

	# Create proper/Debian style paths
	install -d ${D}${sysconfdir}/default/
	install -m 0644 ${S}/crond.sysconfig ${D}${sysconfdir}/default/cron

	# Remove /var/spool/cron as it respectively a link to /etc/cron is part of tuxbox-links
	rm -rf ${D}${localstatedir}/spool/cron 2>/dev/null || true

	# Only install systemd service if we enable systemd
	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		# install systemd unit files
		install -d ${D}${systemd_unitdir}/system
		install -m 0644 ${WORKDIR}/crond.service ${D}${systemd_unitdir}/system/cron.service
		sed -i -e 's,@BASE_BINDIR@,${base_bindir},g' \
			-e 's,@SBINDIR@,${sbindir},g' \
			${D}${systemd_unitdir}/system/cron.service
		# Fix Redhat path to Debian
		sed -e 's#/etc/sysconfig/crond#/etc/default/cron#' -i ${D}${systemd_unitdir}/system/cron.service
	else
		# Only install SysVinit scripts while we have SysVinit
		install -d ${D}${sysconfdir}/init.d/
		install -m 0755 ${WORKDIR}/crond.init ${D}${sysconfdir}/init.d/cron
		# Fix Redhat path to Debian
		sed -e 's#CONFIG=/etc/sysconfig/crond#CONFIG=/etc/default/cron#' -i ${D}${sysconfdir}/init.d/cron
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

FILES_${PN} += "/usr /lib /etc /var"
