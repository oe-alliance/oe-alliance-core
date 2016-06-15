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

SRC_URI[dm520.md5sum] = "b8907aaabc8a08b427ffef8b8b330d9e"
SRC_URI[dm520.sha256sum] = "47a681e31797ff1fcdb5b029065bff519544c7fe7d8615d48f6a7fb9c804b48d"

SRC_URI = "http://dreamboxupdate.com/download/opendreambox/2.2.0/dreambox-secondstage/${PV}/${MACHINE}/${MD5SUM}/dreambox-secondstage_${PV}_${MACHINE}.tar.xz;name=${MACHINE}"

RDEPENDS_${PN} = "flash-scripts"

do_install() {
    install -d ${D}/usr/share/dreambox-secondstage
    install -m 0644 ${S}/usr/share/dreambox-secondstage/ssbl.bin ${D}/usr/share/dreambox-secondstage/ssbl.bin
}

FILES_${PN} = "/usr/share/dreambox-secondstage/ssbl.bin"

COMPATIBLE_MACHINE = "^(dm520)$"