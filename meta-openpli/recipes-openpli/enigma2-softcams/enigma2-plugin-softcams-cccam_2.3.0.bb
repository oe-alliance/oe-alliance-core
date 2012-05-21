DESCRIPTION = "CCcam ${PV} softcam"

PR = "r0"

SRC_URI = "http://downloads.pli-images.org/softcams/CCcam-${PV}.zip \
	file://CCcam.xml"

CAMNAME = "CCcam"

S = "${WORKDIR}/CCcam-${PV}"

require softcam.inc

INHIBIT_PACKAGE_STRIP = "1"

CONFFILES = "/etc/CCcam.cfg /etc/ppanels/CCcam.xml"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}/usr/bin/${CAMNAME}
	install -d ${D}/etc
	install -m 0644 ${S}/CCcam.cfg ${D}/etc/CCcam.cfg
	install -d ${D}/etc/ppanels
	install -m 0644 ${WORKDIR}/CCcam.xml ${D}/etc/ppanels/CCcam.xml
}

SRC_URI[md5sum] = "befff8f25c30dd2a1e18b8885ee0f119"
SRC_URI[sha256sum] = "6b461d95987b7333dfae51280205cd92558bd04c7ef488e37b058c8652201bdf"
