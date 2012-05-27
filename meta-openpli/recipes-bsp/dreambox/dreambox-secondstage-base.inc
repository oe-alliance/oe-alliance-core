SUMMARY = "Dreambox second stage bootloader"
SECTION = "base"
LICENSE = "CLOSED"
DEPENDS += "dreambox-buildimage-native"
PROVIDES += "virtual/bootloader"
RPROVIDES_${PN} += "dreambox-secondstage"
PRIORITY = "required"
INC_PR = "r13"

do_compile() {
        ${DREAMBOX_BUILDIMAGE} \
                --boot-partition ${DREAMBOX_PART0_SIZE}:secondstage-${MACHINE}-${PV}.bin \
                --raw \
                > secondstage-${MACHINE}-${PV}.nand
}
do_install() {
        install -d ${D}${datadir}/dreambox-secondstage
        install -m 0644 secondstage-${MACHINE}-${PV}.bin ${D}${datadir}/dreambox-secondstage/secondstage-${MACHINE}.bin
        install -m 0644 secondstage-${MACHINE}-${PV}.nand ${D}${datadir}/dreambox-secondstage/secondstage-${MACHINE}.nand
}

PACKAGES = "${PN} ${PN}-bin"

PACKAGE_ARCH := "${MACHINE_ARCH}"

RDEPENDS_${PN} = "mtd-utils"

FILES_${PN} = "${datadir}/dreambox-secondstage/secondstage-${MACHINE}.nand"
FILES_${PN}-bin = "${datadir}/dreambox-secondstage/secondstage-${MACHINE}.bin"

pkg_postinst_${PN}() {
if [ -z "$D" ]; then
        flash_erase /dev/mtd1 0 $((${DREAMBOX_PART0_SIZE} / ${DREAMBOX_ERASE_BLOCK_SIZE}))
        nandwrite -m -n -o /dev/mtd1 ${datadir}/dreambox-secondstage/secondstage-${MACHINE}.nand
        sync
fi
}
