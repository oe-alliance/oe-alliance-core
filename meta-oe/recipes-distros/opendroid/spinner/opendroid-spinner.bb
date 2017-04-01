SUMMARY = "opendroid-spinner"
MAINTAINER = "OpenDroid Team"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PV = "6.1"
PR = "r16"

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
	"
        
S = "${WORKDIR}"        

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}

FILES_${PN} = "${datadir}/enigma2"
