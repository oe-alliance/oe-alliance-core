require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

RDEPENDS_${PN} += "mmc-utils"

PV="18.1"
SRCDATE = "20181205"
SRCDATE_PR = "r0"
PR_append = ".1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "d624cc6b35b88c6e221d589e95342abc"
SRC_URI[sha256sum] = "c8ff6058622f8e6cbb904463f25e169caaa39278ffb38046ed7126aa888e304a"