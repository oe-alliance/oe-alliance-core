FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".3"

do_install_append() {
	# When invoked from ifup, delay execution to allow DHCP to assign DNS servers
	# Previously execution via ifup ALWAYS failed.
	perl -i -pe 's:(LOCKFILE=):DELAY=0\n[[ "\$0" == "/etc/network/if-up.d/ntpdate-sync" ]] && DELAY=5\n$1:;' \
		-pe 's:(if) (/usr/sbin/ntpdate -s):$1 sleep \$DELAY && $2:;' \
                 ${D}/usr/bin/ntpdate-sync

	# Only invoke hwclock if it is executable and use stb-hwclock instead ...
	perl -i -pe 's:(if \[ "\$UPDATE_HWCLOCK" = "yes" \]);:$1 && [ -x /sbin/stb-hwclock ];:;' \
                -pe 's:(\s)(hwclock --systohc):$1/sbin/stb-$2:;' \
                 ${D}/usr/bin/ntpdate-sync

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
