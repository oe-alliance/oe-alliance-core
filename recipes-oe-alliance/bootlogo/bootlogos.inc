require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

SRC_URI_append = " file://bootlogo.sh"

INITSCRIPT_NAME = "bootlogo"
INITSCRIPT_PARAMS = "start 21 S ."

inherit update-rc.d

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
	install -d ${D}/usr/share
	install -m 0644 ${WORKDIR}/bootlogo.mvi ${D}/usr/share/bootlogo.mvi
	install -m 0644 ${WORKDIR}/backdrop.mvi ${D}/usr/share/backdrop.mvi
	install -m 0644 ${WORKDIR}/switchoff.mvi ${D}/usr/share/switchoff.mvi
	install -d ${D}${sysconfdir}/enigma2
	install -m 0644 ${WORKDIR}/radio.mvi ${D}${sysconfdir}/enigma2/radio.mvi
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/bootlogo.sh ${D}/${sysconfdir}/init.d/bootlogo
}

PACKAGE_ARCH = "all"
FILES_${PN} = "/ /usr/share /etc/init.d"
