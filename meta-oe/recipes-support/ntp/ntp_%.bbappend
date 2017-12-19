FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".2"

do_install_append() {
	# Only invoke hwclock if it is executable ...
	perl -i -pe 's:(if \[ "\$UPDATE_HWCLOCK" = "yes" \]);:$1 && [ -x /sbin/hwclock ];:' ${D}/usr/bin/ntpdate-sync

	# Don't detach the sync ... the missing time is breaking so many things during the 5 seconds this takes
	#perl -i -pe 's:(^\($|^\)\s+?\&$)::g' ${D}/usr/bin/ntpdate-sync
}

pkg_postinst_ntpdate() {
    if ! grep -q -s ntpdate $D/var/spool/cron/crontabs/root; then
        echo "adding crontab"
        test -d $D/var/spool/cron/crontabs || mkdir -p $D/var/spool/cron/crontabs
        echo "30 * * * *    ${bindir}/ntpdate-sync silent" >> $D/var/spool/cron/crontabs/root
    fi
}
