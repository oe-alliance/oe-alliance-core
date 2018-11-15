require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181115"
SRCDATE_PR = "r0"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "976e641600105ac4986b36b6d8e04e4b"
SRC_URI[sha256sum] = "cd8cd239a8638d40edd562982bd239c5a9dc1514dc2ae8bc3b639d45656388f7"