DESCRIPTION = "openaaf-spinner"
MAINTAINER = "AAF Team"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r2"

SRC_URI="file://wait1.png \
		file://wait2.png \
		file://wait3.png \
		file://wait4.png \
		file://wait5.png \
		file://wait6.png \
		file://wait7.png \
		"
		
S = "${WORKDIR}"		

do_install() {
	install -d ${D}/usr/share/enigma2/skin_default/spinner
	install -m 0644 ${S}/wait1.png ${D}/usr/share/enigma2/skin_default/spinner/wait1.png
	install -m 0644 ${S}/wait2.png ${D}/usr/share/enigma2/skin_default/spinner/wait2.png
	install -m 0644 ${S}/wait3.png ${D}/usr/share/enigma2/skin_default/spinner/wait3.png
	install -m 0644 ${S}/wait4.png ${D}/usr/share/enigma2/skin_default/spinner/wait4.png
	install -m 0644 ${S}/wait5.png ${D}/usr/share/enigma2/skin_default/spinner/wait5.png
	install -m 0644 ${S}/wait6.png ${D}/usr/share/enigma2/skin_default/spinner/wait6.png
	install -m 0644 ${S}/wait7.png ${D}/usr/share/enigma2/skin_default/spinner/wait7.png
}

FILES_${PN} = "/usr/share/enigma2/skin_default/spinner"