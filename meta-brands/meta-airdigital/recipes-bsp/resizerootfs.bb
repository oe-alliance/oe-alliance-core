DESCRIPTION = "Resize Rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^h7$|^h9$|^h9se$|^h9combo$|^h9combose$|^h10$|^i55se$|^i55plus$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "e2fsprogs-resize2fs"
PV = "1.0"
PR = "r0"

SRC_URI = "file://resizerootfs"

inherit update-rc.d

INITSCRIPT_NAME = "resizerootfs"
INITSCRIPT_PARAMS = "start 7 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/resizerootfs ${D}${sysconfdir}/init.d/resizerootfs
}
