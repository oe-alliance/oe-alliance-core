DESCRIPTION = "H9 Bootargs to extend rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^h9$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "1.0"
PR = "r0"

SRC_URI = "file://bootargs-mmc.bin \
   file://bootargs-nand.bin \
   file://bootargs-usb.bin \
"

do_install () {
    install -m 0755 -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/bootargs-usb.bin ${D}/usr/share/bootargs-usb.bin
    install -m 0644 ${WORKDIR}/bootargs-nand.bin ${D}/usr/share/bootargs-nand.bin
    install -m 0644 ${WORKDIR}/bootargs-mmc.bin ${D}/usr/share/bootargs-mmc.bin
}

FILES:${PN} = "/usr/share"
