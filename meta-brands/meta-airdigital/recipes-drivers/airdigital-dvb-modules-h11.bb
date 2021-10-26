KV = "4.4.176"
SRCDATE = "20211026"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-himodules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "b45f81cd4c6facb0a3293a23e2e760ac"
SRC_URI[sha256sum] = "7bfeb5d2c8a96ff9067bf926f6b208b7525cc081a3cbf9da6bc3f9bddc7b0604"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_install:append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	install -m 0755 ${S}/suspend.sh ${D}${sysconfdir}/init.d/suspend
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

do_package_qa() {
}

FILES:${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP:${PN} += "already-stripped ldflags"
