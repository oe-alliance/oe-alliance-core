require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20190424"
SRCDATE_PR = "r0"
PR_append = ".2"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "04115191eae3319ee0109c2c4e7aac6d"
SRC_URI[sha256sum] = "5e29326a7905b90f3960ca59188a4fa5612f5bdfd1e354ecba7dd9949ffba216"