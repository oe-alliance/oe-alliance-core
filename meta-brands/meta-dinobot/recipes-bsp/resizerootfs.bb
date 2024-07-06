DESCRIPTION = "Resize Rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(u5|u51|u52|u53|u54|u56|u57|u571|u532|u533|u5pvr)$"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "e2fsprogs-resize2fs"
PV = "1.0"
PR = "r0"

SRC_URI = "file://resizerootfs"

inherit update-rc.d

INITSCRIPT_NAME = "resizerootfs"
INITSCRIPT_PARAMS = "start 7 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/resizerootfs ${D}${sysconfdir}/init.d/resizerootfs
}
