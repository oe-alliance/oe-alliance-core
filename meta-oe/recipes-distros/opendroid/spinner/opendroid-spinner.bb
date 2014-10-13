SUMMARY = "opendroid-spinner"
MAINTAINER = "opendroid"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"

SRC_URI="file://wait*.png" 
        
S = "${WORKDIR}"        

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}

FILES_${PN} = "${datadir}/enigma2"
