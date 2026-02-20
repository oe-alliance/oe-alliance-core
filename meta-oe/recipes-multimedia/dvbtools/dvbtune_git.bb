SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPL-2.0-only"
DEPENDS = "libxml2"
SRCDATE = "20090621"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCDATE}"
PKGV = "0.0+git${GITPKGV}"

SRC_URI = "git://github.com/linuxstb/dvbtools;protocol=https;branch=master"
S = "${UNPACKDIR}/dvbtune"

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
