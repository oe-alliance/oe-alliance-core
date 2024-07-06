DESCRIPTION = "Resize Rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "8100s"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "e2fsprogs-resize2fs"
PV = "1.0"
PR = "r1"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://resizerootfs"

inherit update-rc.d

INITSCRIPT_NAME = "resizerootfs"
INITSCRIPT_PARAMS = "start 7 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/resizerootfs ${D}${sysconfdir}/init.d/resizerootfs
}
