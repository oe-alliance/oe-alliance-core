KV = "4.4.35"
SRCDATE = "20220505"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES = "virtual/blindscan-dvbs"

require abcom-dvb-modules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "842157ea7bbcf2f97c2ee0d307cd71ab"
SRC_URI[sha256sum] = "452ac81f52b9cfb70cac0ae77a419e5ad3899c66eb5f62a44abd130a9b5dea60"

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
