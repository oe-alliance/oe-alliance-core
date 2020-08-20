KV = "4.4.35"
SRCDATE = "20200731"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES = "virtual/blindscan-dvbs"

require gfutures-dvb-modules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "df338068e0c827ea4587058d2fc5df4e"
SRC_URI[sha256sum] = "afb24a25aeff996994490cf7b7be343dc8ae98205d12b199dba291f2fbd85126"

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
