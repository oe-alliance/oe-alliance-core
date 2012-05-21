CSNAME = "oscam-stable"
DESCRIPTION = "${CSNAME} as cardserver"

RDEPENDS_${PN} = "${CSNAME}"
RCONFLICTS_${PN} = "enigma2-plugin-softcams-${CSNAME} enigma2-plugin-softcams-oscam-unstable enigma2-plugin-softcams-oscam-experimental"

PV = "1.10"
PR = "r0"

SRC_URI = "file://config/${CSNAME}/oscam.*"

S = "${WORKDIR}/${CSNAME}"

INHIBIT_PACKAGE_STRIP = "1"

CSSTART = "exec start-stop-daemon -S -x /usr/bin/${CSNAME} -- -b -r 2 -c /etc/tuxbox/config/${CSNAME}"

require cardserveroscam.inc

CCONFFILES = "/etc/tuxbox/config/${CSNAME}/oscam.conf /etc/tuxbox/config/${CSNAME}/oscam.server /etc/tuxbox/config/${CSNAME}/oscam.srvid /etc/tuxbox/config/${CSNAME}/oscam.user /etc/tuxbox/config/${CSNAME}/oscam.ac /etc/tuxbox/config/${CSNAME}/oscam.cert /etc/tuxbox/config/${CSNAME}/oscam.dvbapi /etc/tuxbox/config/${CSNAME}/oscam.guess /etc/tuxbox/config/${CSNAME}/oscam.ird /etc/tuxbox/config/${CSNAME}/oscam.pem /etc/tuxbox/config/${CSNAME}/oscam.provid /etc/tuxbox/config/${CSNAME}/oscam.services /etc/tuxbox/config/${CSNAME}/oscam.tiers"

do_install() {
	install -d ${D}/etc/tuxbox/config/${CSNAME}
	install -m 0644 ${WORKDIR}/config/${CSNAME}/* ${D}/etc/tuxbox/config/${CSNAME}/
}
