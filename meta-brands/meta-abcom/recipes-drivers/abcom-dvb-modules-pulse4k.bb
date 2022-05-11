KV = "4.4.35"
SRCDATE = "20220504"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES = "virtual/blindscan-dvbs"

require abcom-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "19079eb52cd455b866d0d226c29f8270"
SRC_URI[sha256sum] = "40b5a084eb5090262ba3a5b3c9fe12df1e564d4c568ffba573ad17d52fa4aa2a"

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
