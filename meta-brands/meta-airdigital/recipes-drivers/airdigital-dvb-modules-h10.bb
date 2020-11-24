KV = "4.4.35"
SRCDATE = "20201118"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-himodules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "3e4b5caa71c59e5603c8b0958b892664"
SRC_URI[sha256sum] = "ec13edd7620623c6747bfac7f6f133fdd9725d533c303b6f7c927fa8634515bf"

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