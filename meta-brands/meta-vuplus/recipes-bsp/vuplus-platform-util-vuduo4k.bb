require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20181128"
SRCDATE_PR = "r0"
PR_append = ".1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
	install -m 0755 ${WORKDIR}/bp3flash.sh ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "e3e42c7f5a4c376586e9f14f5a8fbf5c"
SRC_URI[sha256sum] = "1d8f8c24dd560fbc5f8faf1a0d0b28f43ec402f918b3b5656ba7379fd9d82f70"