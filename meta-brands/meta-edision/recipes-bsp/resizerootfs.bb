DESCRIPTION = "Resize Rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "osmio4k"

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
