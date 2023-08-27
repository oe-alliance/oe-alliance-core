SUMMARY = "Dreambox second stage bootloader"
SECTION = "base"
LICENSE = "CLOSED"
PRIORITY = "required"
PROVIDES = "virtual/bootloader"
RPROVIDES:${PN} += "dreambox-secondstage"
MD5SUM = "${@d.getVarFlag('SRC_URI', '%s.md5sum' % MACHINE, True)}"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR = "r7"

S = "${WORKDIR}/dreambox-secondstage_${PV}_${MACHINE}"

SRC_URI[dm7080.md5sum] = "ef12410e7944e23cffaa6753531d7bdd"
SRC_URI[dm7080.sha256sum] = "c50354e66d6f247ab533a518b9df42c2067f6711cd210f9e25983233018df016"

SRC_URI = "https://source.mynonpublic.com/dreambox/dreambox-secondstage_${PV}_${MACHINE}.tar.xz;name=${MACHINE}"

RDEPENDS:${PN} = "flash-scripts"

do_install() {
    install -d ${D}/usr/share/dreambox-secondstage
    install -m 0644 ${S}/usr/share/dreambox-secondstage/ssbl.bin ${D}/usr/share/dreambox-secondstage/ssbl.bin
}

FILES:${PN} = "/usr/share/dreambox-secondstage/ssbl.bin"
