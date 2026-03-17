SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRC = "20170227"

COMPATIBLE_MACHINE = "sf4008"

inherit kernel machine_kernel_pr kernel-fixups

KERNEL_RELEASE = "4.1.37"

SRC_URI[md5sum] = "2ab946924c0b740200bb20f8c49692f0"
SRC_URI[sha256sum] = "a13bc69ec376b568235964103ccb9217efe559b2ebca859f69985f89b91f724a"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = "3"

SRC_URI += "https://source.mynonpublic.com/octagon/octagon-linux-${PV}-${SRC}.tar.xz \
    file://defconfig \
    file://0001-regmap-add-regmap_write_bits.patch \
    file://0002-af9035-fix-device-order-in-ID-list.patch \
    file://0003-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-soloHD.patch \
    ${KERNEL_PATCHES_DVB_USB_SERIES} \
    file://proc_meminfo.patch \
    ${KERNEL_PATCH_TBS_USB_41} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    ${KERNEL_PATCH_MISC_RTL2832_GOTVIEW} \
    ${KERNEL_PATCH_MISC_IF_PORT} \
    file://t230c.patch \
    file://0002-makefile-disable-warnings.patch \
    ${KERNEL_PATCH_BINUTILS241_V2} \
    file://initramfs-chkroot.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-chkroot.cpio.xz ${B}/
}

kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst:kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p3
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm:kernel-image () {
}

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-octagon-${KV}:"

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
