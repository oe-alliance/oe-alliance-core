DESCRIPTION = "Tuxbox common files"
LICENSE = "CLOSED"
MAINTAINER = "PLi team"

PR = "r0"

VERSION := "${PV}"
PV = "${VERSION}+svn${SRCPV}"

SRCREV_FORMAT = "satsxml"

SRC_URI += " ${PLISVNURL}/${PLISVNBRANCH}/cdk/cdk/root;module=share;proto=${PLISVNPROTO};name=satsxml \
	${PLISVNURL}/${PLISVNBRANCH}/cdk/cdk/root;module=etc;proto=${PLISVNPROTO}"

FILES_${PN} = "/"

S = "${WORKDIR}"

inherit allarch

TRANSPONDER_LISTS = "satellites.xml terrestrial.xml"

do_install() {
	install -d ${D}/etc/tuxbox/
	install -d ${D}/usr/share/tuxbox
	install -m 0644 ${S}/share/tuxbox/scart.conf ${D}/etc/tuxbox/scart.conf

	install -m 0644 ${S}/etc/timezone.xml ${D}/etc/tuxbox/timezone.xml
	ln -sf /etc/tuxbox/timezone.xml ${D}/etc/

	ln -sf /usr/share ${D}/share

	for i in ${TRANSPONDER_LISTS}; do
		install -m 0644 ${S}/share/tuxbox/$i ${D}/etc/tuxbox/$i
		ln -sf /etc/tuxbox/$i ${D}/etc/;
		ln -sf /etc/tuxbox/$i ${D}/usr/share/;
		ln -sf /etc/tuxbox/$i ${D}/usr/share/tuxbox/;
	done;
}
