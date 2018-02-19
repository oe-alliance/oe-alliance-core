KV = "4.4.35"
SRCDATE = "20180214"

PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-modules.inc

SRC_URI[md5sum] = "6f845fb36b1e6603f3d54e8650ef2061"
SRC_URI[sha256sum] = "dd409f8cb9588f9b403c56d040e4bd65c064737ba1b7b339ca7961aedba1e15a"

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