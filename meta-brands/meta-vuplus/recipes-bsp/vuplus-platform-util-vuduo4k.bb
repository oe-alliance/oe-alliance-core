require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20181206"
SRCDATE_PR = "r0"
PR_append = ".2"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "3d265c848956aaa76c18ce5e72f77c28"
SRC_URI[sha256sum] = "378f437ce455b4de2108cec0e283c432918b6dcdbcfc3d4d6b516827ef4c63bd"