require vuplus-platform-util.inc

COMPATIBLE_MACHINE = "^(vuduo4k)$"

PV="18.1"
SRCDATE = "20181126"
SRCDATE_PR = "r0"

SRC_URI += "\
	file://bp3flash.zip \
"

do_install_append() {
	install -m 0755 ${WORKDIR}/bp3flash.py ${D}${bindir}
	install -m 0755 ${WORKDIR}/bp3flash.sh ${D}${bindir}
}

S="${WORKDIR}/platform-util-vuduo4k"

SRC_URI[md5sum] = "2cce64917051e6c9aaffe4ac0c511a83"
SRC_URI[sha256sum] = "f94eca89949acccb42e0843d2033bebdd8a9218d5433fe54f2edbd13e23b09ae"