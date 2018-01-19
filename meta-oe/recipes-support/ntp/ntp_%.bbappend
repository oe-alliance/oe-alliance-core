RDEPENDS_ntpdate += "lockfile-progs"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".5"

do_install_append() {
	perl -0777 -i -pe 's:(\. /etc/default/ntpdate.+?fi):$1\n\nif test -f /var/tmp/ntpv4.local ; then\n. /var/tmp/ntpv4.local\nfi\n\ncheck_online() {\n\tcount=0\n\twhile [ \$count -lt 5 ]; do\n\t\tsleep 0.5\n\t\tif ping -4 -c 1 www.google.com >/dev/null 2>&1 \|\| ping -6 -c 1 www.google.com \>/dev/null 2\>&1; then\n\t\t\tbreak\n\t\tfi\n\t\tcount=\$((count+1))\n\tdone\n}\n\nif [ "\$NTPV4" != "" ]; then\n\tNTPSERVERS=\$NTPV4\nfi:s;' \
                 ${D}/usr/bin/ntpdate-sync

	# When invoked from ifup, step to the time rather than slewing
	perl -0777 -i -pe 's:# This is a heuristic.*? then:DELAY=""\n\n# This is a heuristic\: Interfaces are usually brought up during boot, so this is\n# the right time to quickly step to the right time, rather than slewing to it.\nif [ "\$0" = "/etc/network/if-up.d/ntpdate-sync" ]; then\n\tDELAY="check_online":s;' \
                 ${D}/usr/bin/ntpdate-sync

	# When invoked from ifup, wait for network to really be up
	# Previously execution via ifup ALWAYS failed.
	perl -i -pe 's:(if /usr/sbin/ntpdate -s):\$DELAY\n\n$1:;' \
                 ${D}/usr/bin/ntpdate-sync

	# Only invoke hwclock if it is executable and use stb-hwclock instead ...
	perl -i -pe 's:(if \[ "\$UPDATE_HWCLOCK" = "yes" \]);:$1 && [ -x /sbin/stb-hwclock ];:;' \
                -pe 's:(\s)(hwclock --systohc):$1/sbin/stb-$2:;' \
                 ${D}/usr/bin/ntpdate-sync
}

pkg_postinst_ntpdate() {
    if ! grep -q -s ntpdate $D/var/spool/cron/crontabs/root; then
        echo "adding crontab"
        test -d $D/var/spool/cron/crontabs || mkdir -p $D/var/spool/cron/crontabs
        echo "30 * * * *    ${bindir}/ntpdate-sync silent" >> $D/var/spool/cron/crontabs/root
    fi
}
