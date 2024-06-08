DESCRIPTION = "Resize Rootfs"
require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(osmio4k|osmio4kplus|osmini4k)$"

RDEPENDS:${PN} = "e2fsprogs-resize2fs"
PV = "1.0"
PR = "r1"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "\
    file://resizerootfs \
    file://8gb.sh \
"

inherit update-rc.d

INITSCRIPT_NAME = "resizerootfs"
INITSCRIPT_PARAMS = "start 7 S ."

do_install () {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/resizerootfs ${D}${sysconfdir}/init.d/resizerootfs
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/8gb.sh ${D}${bindir}/8gb.sh
}

FILES:${PN} = "${bindir} ${sysconfdir}"
