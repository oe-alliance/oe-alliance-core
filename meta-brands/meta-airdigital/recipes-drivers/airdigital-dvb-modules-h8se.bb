KV = "4.4.35"
SRCDATE = "20260416"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "d630ff3447cb6120d648da01cc1da086"
SRC_URI[sha256sum] = "59a868e26d43499395024675db2fddbbe7cc4fb3f038bf6504fd02ab58380ffe"

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
