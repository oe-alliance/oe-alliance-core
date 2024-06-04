FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/${DISTRO_NAME}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${MACHINE}:"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INITSCRIPT_PARAMS = "start 10 2 3 4 5 . stop 80 0 6 1 ."

do_install:append () {
	install -d ${D}${datadir}/enigma2/defaults
	install -m 0644 ${UNPACKDIR}/interfaces ${D}${datadir}/enigma2/defaults/interfaces
}

FILES:${PN} += " ${datadir}/enigma2/defaults"
