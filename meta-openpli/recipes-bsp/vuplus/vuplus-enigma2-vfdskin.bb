require conf/license/openpli-gplv2.inc

RDEPENDS_${PN} = "enigma2"

PR = "r0"

SRC_URI = "file://skin_user.xml \
	file://vfd_icons \
"

do_install() {
	install -d ${D}/usr/share/enigma2/defaults/
	install -m 0644 ${WORKDIR}/skin_user.xml ${D}/usr/share/enigma2/defaults/
        install -d ${D}/usr/share/enigma2/vfd_icons/
        install -m 0644 ${WORKDIR}/vfd_icons/*.png ${D}/usr/share/enigma2/vfd_icons/
}

FILES_${PN} = "/usr/share/enigma2/defaults"
FILES_${PN} += "/usr/share/enigma2/vfd_icons"

PACKAGE_ARCH = "${MACHINE_ARCH}"
