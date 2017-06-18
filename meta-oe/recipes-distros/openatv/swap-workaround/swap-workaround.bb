SUMMARY = "swap workaround for box with low memory"
MAINTAINER = "ATV Team"
SECTION = "base"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI="file://swapfile"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${WORKDIR}/swapfile    ${D}${sysconfdir}/init.d/swapfile
    ln -sf        ../init.d/swapfile    ${D}${sysconfdir}/rc3.d/S98swapfile
}