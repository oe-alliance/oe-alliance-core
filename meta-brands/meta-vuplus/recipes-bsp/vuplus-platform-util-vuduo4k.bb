require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181121"
SRCDATE_PR = "r4"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "25702f2aff0297293feb99f39988edeb"
SRC_URI[sha256sum] = "d73b028321a794df433c87c42b54f95161d5fc1a0b264e7f18e7ada911e55bca"