KV = "4.4.35"
SRCDATE = "20260407"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "a204bb47501f9205d448b21749c25f85"
SRC_URI[sha256sum] = "21851f908c8a58c49a5f02455ea92dceb35b25b736b348b11404da4ac76ba827"

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
