SUMMARY = "stb-hwclock - Trivial script to load the current fp time into the kernel clock"
HOMEPAGE = ""
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

PV = "1.2"

SRC_URI = "file://stb-hwclock \
           file://stb-hwclock.init \
"
inherit update-rc.d

INITSCRIPT_NAME = "stb-hwclock"
INITSCRIPT_PARAMS = "start 67 S ."

#SYSTEMD_PACKAGES = "${PN}"
#SYSTEMD_SERVICE_${PN} = "stb-hwclock.service"

do_configure() {
}

do_compile() {
}

do_install() {
    install -d ${D}${base_sbindir}
    install -m 755 ${WORKDIR}/stb-hwclock ${D}${base_sbindir}

#    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
#        install -d ${D}${systemd_system_unitdir}
#        install -m 0644 ${WORKDIR}/stb-hwclock.service ${D}${systemd_system_unitdir}
#    else
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/stb-hwclock.init ${D}${sysconfdir}/init.d/stb-hwclock
#    fi
}
