require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181116"
SRCDATE_PR = "r1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "9a2da169e299c72ed41e588719688911"
SRC_URI[sha256sum] = "bafa1a4cd437d434f9522ae16b4b325dc1e7118415e14aee5876928190f9ca96"