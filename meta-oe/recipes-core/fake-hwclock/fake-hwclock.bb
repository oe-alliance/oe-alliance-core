SUMMARY = "fake-hwclock - Trivial script to load/save current contents of the kernel clock"
HOMEPAGE = ""
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

PV = "1.0"

SRC_URI = "file://fake-hwclock \
           file://fake-hwclock.init \
           file://fake-hwclock.default \
"
inherit update-rc.d

INITSCRIPT_NAME = "fake-hwclock"
INITSCRIPT_PARAMS = "start 01 S . stop 25 0 6 ."

#SYSTEMD_PACKAGES = "${PN}"
#SYSTEMD_SERVICE_${PN} = "fake-hwclock.service"

do_configure() {
}

do_compile() {
}

do_install() {
    install -d ${D}${base_bindir}
    install -m 755 ${WORKDIR}/fake-hwclock ${D}${base_bindir}

    install -d ${D}${sysconfdir}/default
    install -m 644 ${WORKDIR}/fake-hwclock.default ${D}${sysconfdir}/default/fake-hwclock


#    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
#        install -d ${D}${systemd_system_unitdir}
#        install -m 0644 ${WORKDIR}/fake-hwclock.service ${D}${systemd_system_unitdir}
#    else
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/fake-hwclock.init ${D}${sysconfdir}/init.d/fake-hwclock
#    fi
}

pkg_postinst_${PN}_prepend () {
#!/bin/sh
if [ -n "$D" ]; then
        [[ -f $D/etc/fake-hwclock.data ]] || date -u '+%Y-%m-%d %H:%M:%S' > $D/etc/fake-hwclock.data
fi
}
