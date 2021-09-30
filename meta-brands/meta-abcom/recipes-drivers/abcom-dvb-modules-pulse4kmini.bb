KV = "4.4.35"
SRCDATE = "20210910"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES = "virtual/blindscan-dvbs"

require abcom-dvb-modules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "840b78273f9fb32b744dbc06650bcd02"
SRC_URI[sha256sum] = "b879cefa9796729d65f227d83733a98baaf192c53e0e968ee291eda4878156f1"

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
