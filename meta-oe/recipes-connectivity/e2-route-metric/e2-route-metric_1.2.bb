SUMMARY = "Automatic LAN/WLAN route preference for Enigma2"
DESCRIPTION = "Maintains a dedicated policy-routing table when multiple IPv4 or IPv6 gateway interfaces are configured. Wired interfaces are preferred over wireless interfaces while existing connected, static and VPN routes in the main table remain effective. Enigma2's generated network configuration is not modified."
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://e2-route-metric \
           file://e2-route-metric-monitor.c \
           file://e2-route-metric.init \
           file://e2-route-metric.default \
           file://udhcpc-route-metric \
           file://ifup-route-metric \
           file://ifdown-route-metric \
"

S = "${UNPACKDIR}"
PR = "r0"

inherit update-rc.d

INITSCRIPT_NAME = "e2-route-metric"
INITSCRIPT_PARAMS = "start 35 2 3 4 5 . stop 65 0 1 6 ."

RDEPENDS:${PN} = "busybox iproute2"

CFLAGS:append = " -Os -ffunction-sections -fdata-sections"
LDFLAGS:append = " -Wl,--gc-sections"

do_compile() {
    ${CC} ${CFLAGS} ${CPPFLAGS} -Wall -Wextra -Werror ${LDFLAGS} \
        ${S}/e2-route-metric-monitor.c -o ${B}/e2-route-metric-monitor
}

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 ${S}/e2-route-metric ${D}${sbindir}/e2-route-metric
    install -m 0755 ${B}/e2-route-metric-monitor ${D}${sbindir}/e2-route-metric-monitor

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/e2-route-metric.init ${D}${sysconfdir}/init.d/e2-route-metric

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${S}/e2-route-metric.default ${D}${sysconfdir}/default/e2-route-metric

    install -d ${D}${sysconfdir}/udhcpc.d
    install -m 0755 ${S}/udhcpc-route-metric ${D}${sysconfdir}/udhcpc.d/90e2-route-metric

    install -d ${D}${sysconfdir}/network/if-up.d
    install -m 0755 ${S}/ifup-route-metric ${D}${sysconfdir}/network/if-up.d/90e2-route-metric

    install -d ${D}${sysconfdir}/network/if-down.d
    install -m 0755 ${S}/ifdown-route-metric ${D}${sysconfdir}/network/if-down.d/90e2-route-metric
}

CONFFILES:${PN} = "${sysconfdir}/default/e2-route-metric"

pkg_postinst:${PN}() {
    if [ -z "$D" ]; then
        /etc/init.d/e2-route-metric restart >/dev/null 2>&1 || true
    fi
}

pkg_prerm:${PN}() {
    if [ -z "$D" ] && [ -x /etc/init.d/e2-route-metric ]; then
        /etc/init.d/e2-route-metric stop >/dev/null 2>&1 || true
    fi
}
