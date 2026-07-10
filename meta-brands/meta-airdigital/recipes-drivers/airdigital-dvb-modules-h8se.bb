KV = "4.4.35"
SRCDATE = "20260710"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "e4dd7d2224077f8b9b32e86436c65f27"
SRC_URI[sha256sum] = "710ce3176232cb4263f6aea1678e023e25a8543390c1cff00fce1e902450df07"

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
