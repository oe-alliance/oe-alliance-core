KV = "4.4.35"
SRCDATE = "20190603"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require maxytec-dvb-modules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "ef0b3ffe637599b12aa52a491669ad8a"
SRC_URI[sha256sum] = "0b52f40924ea1460daf426676af7a40f3dd72b7c0a1e93b1736ee74851a7ea93"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	install -m 0755 ${S}/suspend.sh ${D}${sysconfdir}/init.d/suspend
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}


FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped ldflags"