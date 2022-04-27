KV = "4.4.176"
SRCDATE = "20220424"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "66002913d9d228c5bb31638f141c1933"
SRC_URI[sha256sum] = "4c14bd9cc9d615add396edb70e751bfd1185eb3223a93974a733baad64723f8d"

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