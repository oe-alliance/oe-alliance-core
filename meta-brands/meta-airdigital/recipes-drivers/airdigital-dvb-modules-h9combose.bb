KV = "4.4.35"
SRCDATE = "20210727"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-himodules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "2f6092d1430ce822f363cb2937dddace"
SRC_URI[sha256sum] = "f5f71b9835f274aee912bd6671616602be8cd40f7a2319508c29f7d0d71d5ad2"

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