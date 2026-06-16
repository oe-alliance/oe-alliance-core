SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPL-2.0-or-later"
DEPENDS = "libxml2"
SRCDATE = "20090621"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCDATE}"
PKGV = "0.0+git${GITPKGV}"

SRC_URI = "git://github.com/linuxstb/dvbtools;protocol=https;branch=master"
S = "${UNPACKDIR}/dvbstream"

CFLAGS:append = " ${LDFLAGS} -D_GNU_SOURCE"

do_install() {
    mkdir -p ${D}${bindir}
    for i in dvbstream dumprtp ts_filter rtpfeed; do install -m 0755 $i ${D}${bindir}/; done
}
