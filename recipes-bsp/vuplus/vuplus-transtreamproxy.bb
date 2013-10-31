DESCRIPTION = "streamproxy manages streaming data to a Mobile device using enigma2"
LICENSE = "CLOSED"

inherit gitpkgv
SRCREV="e6d6277466860e4dd779f566cead24a360a87d43"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "3"

SRC_URI = "git://schwerkraft.elitedvb.net/streamproxy/streamproxy.git;protocol=git \
	file://transcoding.patch \
	"

inherit autotools

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/src/streamproxy ${D}/usr/bin/transtreamproxy
}

