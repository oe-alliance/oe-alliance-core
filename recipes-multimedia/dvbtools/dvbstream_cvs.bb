SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2+"
DEPENDS = "libxml2"
SRCDATE = "20090621"
PV = "0.0+cvs${SRCDATE}"

PR = "r1"

SRC_URI = "cvs://anonymous@dvbtools.cvs.sourceforge.net/cvsroot/dvbtools;module=dvbstream"
S = "${WORKDIR}/dvbstream"

CFLAGS_append = " ${LDFLAGS} -D_GNU_SOURCE"

do_install() {
	mkdir -p ${D}${bindir}
	for i in dvbstream dumprtp ts_filter rtpfeed; do install -m 0755 $i ${D}${bindir}/; done
}
