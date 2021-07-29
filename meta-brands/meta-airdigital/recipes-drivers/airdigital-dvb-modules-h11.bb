KV = "4.4.176"
SRCDATE = "20210727"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-himodules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "6aae61d0c116afd6e6c06cb991644f36"
SRC_URI[sha256sum] = "4085cd68567127a5fc4e0b631ee6967a5959ef18cc55d5e031a17fd53e3eee35"

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
