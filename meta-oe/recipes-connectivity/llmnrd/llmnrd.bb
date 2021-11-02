SUMMARY = "llmnrd - Link-Local Multicast Resolution (LLMNR) Daemon for Linux"
HOMEPAGE = "https://github.com/tklauser/llmnrd"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/tklauser/llmnrd.git;protocol=https \
    file://llmnrd.sh \
"

PACKAGES =+ "llmnr-query"
FILES_llmnr-query = "${bindir}/llmnr-query"

S = "${WORKDIR}/git"

inherit pkgconfig update-rc.d systemd

INITSCRIPT_NAME = "llmnrd"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "llmnrd.service"

EXTRA_OEMAKE = " \
    'CC=${CC}' \
"

do_compile() {
    oe_runmake CROSS_COMPILE="${TARGET_PREFIX}"
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/llmnrd ${D}${bindir}
    install -m 755 ${S}/llmnr-query ${D}${bindir}
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${S}/etc/llmnrd.service ${D}${systemd_system_unitdir}
        perl -i -pe 's:/usr/sbin:${bindir}:' ${D}${systemd_system_unitdir}/*.service
    else
        install -d ${D}${sysconfdir}/init.d
        install -m 755 ${WORKDIR}/llmnrd.sh ${D}${sysconfdir}/init.d/llmnrd
    fi
}
