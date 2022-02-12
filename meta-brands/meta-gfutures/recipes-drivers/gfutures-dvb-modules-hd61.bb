KV = "4.4.35"
SRCDATE = "20220208"

RDEPENDS:${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES = "virtual/blindscan-dvbs"

require gfutures-dvb-modules.inc

SRC_URI:append = " file://suspend.sh"

SRC_URI[md5sum] = "6cfab3b1137f2ae1b8a51ead4071b441"
SRC_URI[sha256sum] = "a69f09a45b19a3cbd7656ee90ba037126dba4e2db06fc8efdab64a811ded4653"

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
