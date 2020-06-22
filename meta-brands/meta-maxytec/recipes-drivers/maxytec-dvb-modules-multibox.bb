KV = "4.4.35"
SRCDATE = "20200622"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require maxytec-dvb-modules.inc

SRC_URI_append = " file://suspend.sh"

SRC_URI[md5sum] = "99484caab1163d69ea9c04db1dd82dd0"
SRC_URI[sha256sum] = "58aad3348c457eb77d91538425889553004fd93a9be617d7d7cb60b9ab429db1"

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
