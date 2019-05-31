SUMMARY = "Dreambox second stage bootloader"
SECTION = "base"
LICENSE = "CLOSED"
PRIORITY = "required"
PROVIDES = "virtual/bootloader"
RPROVIDES_${PN} += "dreambox-secondstage"
MD5SUM = "${@d.getVarFlag('SRC_URI', '%s.md5sum' % MACHINE, True)}"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR = "r0"

S = "${WORKDIR}/dreambox-secondstage_${PV}_${MACHINE}"

SRC_URI[dm520.md5sum] = "e1b388c62396e57b3c359fcc922eedad"
SRC_URI[dm520.sha256sum] = "d2253ab36ee0871206d019126e15750ae1eaefccf4cb11b6dca834d16f81c415"

SRC_URI = "http://dreamboxupdate.com/download/opendreambox/2.2.0/dreambox-secondstage/${PV}/${MACHINE}/${MD5SUM}/dreambox-secondstage_${PV}_${MACHINE}.tar.xz;name=${MACHINE}"

RDEPENDS_${PN} = "flash-scripts"

do_install() {
    install -d ${D}/usr/share/dreambox-secondstage
    install -m 0644 ${S}/usr/share/dreambox-secondstage/ssbl.bin ${D}/usr/share/dreambox-secondstage/ssbl.bin
}

FILES_${PN} = "/usr/share/dreambox-secondstage/ssbl.bin"

COMPATIBLE_MACHINE = "^(dm520)$"
