FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR .= ".1"

do_install_append() {
	# Only invoke hwclock if it is executable ...
	perl -i -pe 's:(if \[ "\$UPDATE_HWCLOCK" = "yes" \]);:$1 && [ -x /sbin/hwclock ];:' ${D}/usr/bin/ntpdate-sync

	# Don't detach the sync ... the missing time is breaking so many things during the 5 seconds this takes
	#perl -i -pe 's:(^\($|^\)\s+?\&$)::g' ${D}/usr/bin/ntpdate-sync
}

