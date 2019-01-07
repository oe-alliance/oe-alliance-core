require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20190107"
SRCDATE_PR = "r0"
PR_append = ".2"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "8b2b8f3c6424581b27331a086df5d456"
SRC_URI[sha256sum] = "78fa3ca444a2d1e73075619b3b0be20f2b45c67ea6d8c50f18666c8866ba74be"