require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181119"
SRCDATE_PR = "r1"

SRC_URI += "\
	file://bp3flash.tar.gz \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "d2e9b603d22bc9461b3d77bd7a0e305e"
SRC_URI[sha256sum] = "458bf0bd9221ec686200d964e69e0b669fde37dca526fbcc7d69f52f0b1edd0a"