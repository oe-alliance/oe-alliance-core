require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181120"
SRCDATE_PR = "r1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "22296d452f0b4a7d7b6401a2afc09d0e"
SRC_URI[sha256sum] = "1cb755a65877fbcfb0a46b2a4fb49512e3b543cc1f2d51b3dc7a56aa78b34fd0"