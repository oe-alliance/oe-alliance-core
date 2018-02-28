KV = "4.4.35"
SRCDATE = "20180227"

PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-modules.inc

SRC_URI[md5sum] = "f0683b09062b30c9efd6c639c26939d2"
SRC_URI[sha256sum] = "99768ee6107bb6fab6fa504620aff6175d2bd9d94b42dc3da8713191598b7245"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	echo "#!/bin/sh" > ${S}/suspend
	echo "mount -t sysfs sys /sys" >> ${S}/suspend
	echo "/usr/bin/turnoff_power" >> ${S}/suspend
	install -m 0755 ${S}/suspend ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

pkg_prerm_${PN}() {
	if [ "x$D" == "x" ]; then
		if [ -f /lib/modules/${KV}/extra/hi_play.ko ] ; then
			rm -f /lib/modules/${KV}/extra/hi_play.ko;
		fi
	fi
} 

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped ldflags file-rdeps"