SUMMARY = "management framework for resolv.conf"
AUTHOR = "Roy Marples <roy@marples.name>"
HOMEPAGE = "http://roy.marples.name/projects/openresolv"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://resolvconf.in;beginline=4;endline=26;md5=e962049f535f7385f0f2a0ac9638cd43"
PR = "r0"

SRC_URI = "http://roy.marples.name/downloads/${BPN}/${BP}.tar.bz2 \
           file://000resolvconf.if-up \
           file://000resolvconf.ppp.ip-down \
           file://000resolvconf.ppp.ip-up \
           file://resolvconf.conf \
           file://resolvconf.if-down \
           file://volatiles.99_openresolv"
SRC_URI[md5sum] = "76337107ff56d7450d4ed622630c5574"
SRC_URI[sha256sum] = "4a4cf4ef20a1ce207937fb669bbae43f9db36f6298090b5e074c80ed2fe4a996"

inherit allarch

do_configure() {
        echo "SYSCONFDIR=${sysconfdir}" > config.mk
        echo "SBINDIR=${base_sbindir}" >> config.mk
        echo "LIBEXECDIR=/lib/resolvconf" >> config.mk
        echo "VARDIR=${localstatedir}/run/resolvconf" >> config.mk
        echo "MANDIR=${mandir}" >> config.mk
        echo "RCDIR=${sysconfdir}/init.d" >> config.mk
        echo "RESTARTCMD=if ${sysconfdir}/init.d/\1 status >/dev/null 2>\&1; then ${sysconfdir}/init.d/\1 restart; fi" >> config.mk
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

RPROVIDES_${PN} = "resolvconf"

RCONFLICTS_${PN} = "resolvconf"

FILES_${PN} += "/lib/resolvconf"

pkg_postinst_${PN}() {
if [ -z "$D" -a -x ${sysconfdir}/init.d/populate-volatile.sh ]; then
	${sysconfdir}/init.d/populate-volatile.sh update
fi
}
pkg_postrm_${PN}() {
if [ -z "$D" -a -x ${sysconfdir}/init.d/populate-volatile.sh ]; then
	${sysconfdir}/init.d/populate-volatile.sh update
fi
}
