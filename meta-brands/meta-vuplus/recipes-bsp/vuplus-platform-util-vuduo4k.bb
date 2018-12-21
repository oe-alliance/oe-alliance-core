require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20181220"
SRCDATE_PR = "r0"
PR_append = ".2"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "fdd81b9b688864df1f60d3ebd9d03a40"
SRC_URI[sha256sum] = "a928b75fb3587f97140d5716139e486a8fae4b09ad96b31751856c28566badc3"