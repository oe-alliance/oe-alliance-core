SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
DEPENDS = "libxml2"
SRCDATE = "20090621"
PV = "0.0+cvs${SRCDATE}"

SRC_URI = "cvs://anonymous@dvbtools.cvs.sourceforge.net/cvsroot/dvbtools;module=dvbtune"

S = "${WORKDIR}/dvbtune"

CFLAGS += "\$(shell xml2-config --cflags)"
LDFLAGS += "\$(shell xml2-config --libs)"

do_compile() {
	oe_runmake dvbtune xml2vdr
}

do_install() {
	mkdir -p ${D}${bindir}
	install -m 0755 dvbtune ${D}${bindir}/
	install -m 0755 xml2vdr ${D}${bindir}/
}
