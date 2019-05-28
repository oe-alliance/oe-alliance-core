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

SRC_URI[dm820.md5sum] = "597f55dbc5b87133d305690db41c0c8e"
SRC_URI[dm820.sha256sum] = "26afa1d0922f95bedf3856b8ad7866453c2ffc1da2d2cee17b79f11fa077afe6"

SRC_URI = "http://dreamboxupdate.com/download/opendreambox/2.2.0/dreambox-secondstage/${PV}/${MACHINE}/${MD5SUM}/dreambox-secondstage_${PV}_${MACHINE}.tar.xz;name=${MACHINE}"

RDEPENDS_${PN} = "flash-scripts"

do_install() {
    install -d ${D}/usr/share/dreambox-secondstage
    install -m 0644 ${S}/usr/share/dreambox-secondstage/ssbl.bin ${D}/usr/share/dreambox-secondstage/ssbl.bin
}

FILES_${PN} = "/usr/share/dreambox-secondstage/ssbl.bin"

COMPATIBLE_MACHINE = "^(dm820)$"
