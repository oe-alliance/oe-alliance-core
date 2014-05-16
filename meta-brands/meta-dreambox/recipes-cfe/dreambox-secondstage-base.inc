SUMMARY = "Dreambox second stage bootloader"
SECTION = "base"
LICENSE = "CLOSED"
PROVIDES += "virtual/bootloader"
RPROVIDES_${PN} += "dreambox-secondstage"
PRIORITY = "required"
INC_PR = "r20"

do_install() {
    install -d ${D}/tmp
    buildimage --arch ${MACHINE} --raw ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
    --flash-size ${DREAMBOX_FLASH_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZE} \
    --boot-partition=${DREAMBOX_PART0_SIZE}:secondstage-${MACHINE}-${PV}.bin \
    > ${D}/tmp/secondstage-${MACHINE}.bin
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0644 ${S}/secondstage-${MACHINE}-${PV}.bin ${DEPLOY_DIR_IMAGE}/secondstage-${MACHINE}.bin
}

PACKAGES = "${PN} ${PN}-bin"

PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "dreambox-buildimage-native"

# DM8000 needs mtd-utils nandwrite for the secondstage update, 7405 nand driver works fine with busybox nandwrite
RDEPENDS_${PN}_dm8000 = "mtd-utils"

FILES_${PN} = "/tmp/secondstage-${MACHINE}.bin"

pkg_postinst_${PN}() {
    if [ -z "$D" ] && grep -q '\<${MACHINE}\>' /proc/stb/info/model; then
        if [ -f /tmp/secondstage-${MACHINE}.bin ]; then
            # first try mtd-utils, and fallback to busybox applets
            flash_erase /dev/mtd1 0 0 2>/dev/null || flash_eraseall /dev/mtd1
            nandwrite -m -n -o /dev/mtd1 /tmp/secondstage-${MACHINE}.bin 2>/dev/null || nandwrite -o /dev/mtd1 /tmp/secondstage-${MACHINE}.bin
            rm /tmp/secondstage-${MACHINE}.bin
        fi
    fi
}
