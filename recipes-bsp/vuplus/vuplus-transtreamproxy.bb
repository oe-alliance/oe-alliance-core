DESCRIPTION = "streamproxy manages streaming data to a Mobile device using enigma2"
LICENSE = "CLOSED"

SRCDATE = "20101014"
PV = "1.0cvs${SRCDATE}"

SRC_URI = "cvs://anonymous@cvs.schwerkraft.elitedvb.net/cvsroot/streamproxy;module=enigma2-streamproxy;method=pserver \
	file://transcoding.patch \
	"

inherit autotools

S = "${WORKDIR}/enigma2-streamproxy"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/src/streamproxy ${D}/usr/bin/transtreamproxy
}

