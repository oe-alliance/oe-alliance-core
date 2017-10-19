FILESEXTRAPATHS_prepend := "${THISDIR}/cronie:"

PROVIDES += "virtual/cron"
RPROVIDES_${PN} += "virtual/cron"
RCONFLICTS_${PN} += "busybox-cron"

CONFFILES_${PN} += "/etc/cron.deny /etc/crontab /etc/default/crond"

do_install_append() {
	sed -e 's#CONFIG=/etc/sysconfig/crond#CONFIG=/etc/default/crond#' -i ${D}${sysconfdir}/init.d/crond
	install -d 755 ${D}${sysconfdir}/default
	mv ${D}${sysconfdir}/sysconfig/crond ${D}${sysconfdir}/default
}
