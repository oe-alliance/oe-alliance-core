SUMMARY = "Expose the running GTX raw-kernel system to OE-Alliance MultiBoot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r3"
SRC_URI = "file://gtx-multiboot"
S = "${UNPACKDIR}"
RDEPENDS:${PN} += "ofgwrite util-linux-sfdisk util-linux-partx e2fsprogs-resize2fs"
inherit update-rc.d
INITSCRIPT_NAME = "gtx-multiboot"
INITSCRIPT_PARAMS = "start 41 S ."

do_install() {
    install -D -m 0755 ${UNPACKDIR}/gtx-multiboot ${D}${sysconfdir}/init.d/gtx-multiboot
}
