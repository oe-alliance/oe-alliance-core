KV = "4.4.176"
SRCDATE = "20210305"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-himodules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "0963c110eea6d653f4a7563dcbf2b384"
SRC_URI[sha256sum] = "ac5c59a0d7d1dea2895b60d36528a9cb9592bc7606dedc0c53a04f71952deaa1"

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
