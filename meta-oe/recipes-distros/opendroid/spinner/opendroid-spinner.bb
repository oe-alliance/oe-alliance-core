SUMMARY = "opendroid-spinner"
MAINTAINER = "OpenDroid Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

PV = "6.5"
PR = "r23"

SRC_URI="file://wait*.png" 
        
S = "${WORKDIR}"        

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}

FILES_${PN} = "${datadir}/enigma2"
