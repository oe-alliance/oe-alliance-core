KV = "4.4.35"
SRCDATE = "20260728"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "41266708531e00648d13ab46582805ac"
SRC_URI[sha256sum] = "b502ee6d9ba04bced07195585910b8b2da2f3cbde2d5c8d58fc2456a12ed00fd"

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
