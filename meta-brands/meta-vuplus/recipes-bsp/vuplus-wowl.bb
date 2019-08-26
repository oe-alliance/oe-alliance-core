SUMMARY = "WOWL"
MAINTAINER = "vuplus team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r1"
SRC_REV = ""

SRC_URI = "\
   file://wowl.sh \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d \
    ${D}${bindir} \
    ${D}${sysconfdir}/rc0.d 
    install -m 0755 ${WORKDIR}/wowl.sh ${D}${sysconfdir}/init.d/wowl.sh
    ln -sf   ../init.d/wowl.sh ${D}${sysconfdir}/rc0.d/K32wowl.sh
}

INHIBIT_PACKAGE_STRIP = "1"
FILES_${PN} = "/"
