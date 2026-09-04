KV = "4.4.35"
SRCDATE = "20260729"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "2a1e64e3eaa4d5d5b5d10159c0399eb7"
SRC_URI[sha256sum] = "504cff74d454d6b0871ced4ec217603253ba43b8df9f099bff0226bdccfd7a46"

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
