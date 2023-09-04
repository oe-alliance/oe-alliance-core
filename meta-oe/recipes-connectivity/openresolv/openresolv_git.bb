SUMMARY = "management framework for resolv.conf"
AUTHOR = "Roy Marples <roy@marples.name>"
HOMEPAGE = "https://github.com/NetworkConfiguration/openresolv"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://resolvconf.in;beginline=4;endline=26;md5=e962049f535f7385f0f2a0ac9638cd43"

inherit allarch gitpkgv

SRCREV = "${AUTOREV}"
PV = "3.13.1+git"
PKGV = "3.13.1+git${GITPKGV}"

SRC_URI = "git://github.com/NetworkConfiguration/openresolv;protocol=https;branch=master \
           file://000resolvconf.if-up \
           file://000resolvconf.ppp.ip-down \
           file://000resolvconf.ppp.ip-up \
           file://resolvconf.conf \
           file://resolvconf.if-down \
           file://volatiles.99_openresolv"

do_configure() {
        echo "SYSCONFDIR=${sysconfdir}" > config.mk
        echo "SBINDIR=${base_sbindir}" >> config.mk
        echo "LIBEXECDIR=/lib/resolvconf" >> config.mk
        echo "VARDIR=${localstatedir}/run/resolvconf" >> config.mk
        echo "MANDIR=${mandir}" >> config.mk
        echo "RCDIR=${sysconfdir}/init.d" >> config.mk
}

do_install() {
        oe_runmake "DESTDIR=${D}" install
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/resolvconf.conf ${D}${sysconfdir}/resolvconf.conf
        install -d ${D}${sysconfdir}/default/volatiles
        install -m 0644 ${WORKDIR}/volatiles.99_openresolv ${D}${sysconfdir}/default/volatiles/99_openresolv
        install -d ${D}${sysconfdir}/network/if-down.d
        install -m 0755 ${WORKDIR}/resolvconf.if-down ${D}${sysconfdir}/network/if-down.d/resolvconf
        install -d ${D}${sysconfdir}/network/if-up.d
        install -m 0755 ${WORKDIR}/000resolvconf.if-up ${D}${sysconfdir}/network/if-up.d/000resolvconf
        install -d ${D}${sysconfdir}/ppp/ip-down.d
        install -m 0755 ${WORKDIR}/000resolvconf.ppp.ip-down ${D}${sysconfdir}/ppp/ip-down.d/000resolvconf
        install -d ${D}${sysconfdir}/ppp/ip-up.d
        install -m 0755 ${WORKDIR}/000resolvconf.ppp.ip-up ${D}${sysconfdir}/ppp/ip-up.d/000resolvconf
}

S = "${WORKDIR}/git"

RPROVIDES:${PN} = "resolvconf"

RCONFLICTS:${PN} = "resolvconf"

FILES:${PN} += "${baselib}/resolvconf"

pkg_postinst:${PN}() {
if [ -z "$D" -a -x ${sysconfdir}/init.d/populate-volatile.sh ]; then
    ${sysconfdir}/init.d/populate-volatile.sh update
fi
}

pkg_postrm:${PN}() {
if [ -z "$D" -a -x ${sysconfdir}/init.d/populate-volatile.sh ]; then
    ${sysconfdir}/init.d/populate-volatile.sh update
fi
}
