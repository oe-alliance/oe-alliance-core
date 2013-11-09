DESCRIPTION = "swf-spinner"
MAINTAINER = "swf"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r6"

SRC_URI="file://wait1.png \
		file://wait2.png \
		file://wait3.png \
		file://wait4.png \
		file://wait5.png \
		file://wait6.png \
		file://wait7.png \
		file://wait8.png \
		file://wait9.png \
		file://wait10.png \
		file://wait11.png \
		file://wait12.png \
		file://wait13.png \
		file://wait14.png \
		file://wait15.png \
		file://wait16.png \
		file://wait17.png \
		file://wait18.png \
		file://wait19.png \
		file://wait20.png \
		file://wait21.png \
		file://wait22.png \
		file://wait23.png \
		file://wait24.png \
		file://wait25.png \
		file://wait26.png \
		file://wait27.png \
		file://wait28.png \
		file://wait29.png \
		file://wait30.png \
		file://wait31.png \
		file://wait32.png \
		file://wait33.png \
		file://wait34.png \
		file://wait35.png \
		file://wait36.png \
		file://wait37.png \
		file://wait38.png \
		file://wait39.png \
		file://wait40.png \
		"

		
S = "${WORKDIR}"		

do_install() {
	install -d ${D}${datadir}/enigma2/spinner
	install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}

FILES_${PN} = "${datadir}/enigma2"
