SUMMARY = "OE-Alliance Linux Core - minimal bootable Linux system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} = "\
    packagegroup-core-boot \
    bash \
    cronie \
    early-configure \
    elfutils \
    e2fsprogs-mke2fs \
    modutils-loadscript \
    opkg \
    parted \
    procps \
    rc-local \
    7zip \
    tzdata \
    minilocale \
    util-linux-sfdisk \
    util-linux-blkid \
    util-linux-flock \
    volatile-media \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", "${LINUX_CORE_EXTENDED}", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "emmc", "dosfstools mtools e2fsprogs-resize2fs partitions-by-name gptfdisk", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "fastboot", "dosfstools mtools android-tools", "", d)} \
    "

LINUX_CORE_EXTENDED = "\
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    sdparm \
    mtd-utils \
    mtd-utils-ubifs \
    "

RRECOMMENDS:${PN} = "\
    kernel-module-xfs \
    kernel-module-exportfs \
    kernel-module-fat \
    kernel-module-msdos \
    kernel-module-vfat \
    kernel-module-nls-cp437 \
    kernel-module-nls-iso8859-1 \
    kernel-module-nls-iso8859-15 \
    "
