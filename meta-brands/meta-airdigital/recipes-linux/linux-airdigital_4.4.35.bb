DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

KERNEL_RELEASE = "4.4.35"

SRCDATE = "20200508"

COMPATIBLE_MACHINE = "^(h8|h9|h9se|h9combo|h9combose|h10|hzero|i55plus|i55se)$"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = "34"

SRC_URI[md5sum] = "f9e67e2d0ceab518510413f8f4315bc3"
SRC_URI[sha256sum] = "45ae717b966a74326fd7297d81b3a17fd5b3962b7704170682a615ca7cdec644"

SRC_URI = "https://source.mynonpublic.com/zgemma/linux-${PV}-${SRCDATE}-${ARCH}.tar.gz \
    file://defconfig \
    ${KERNEL_PATCHES_SERIES_4_4_35} \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://initramfs-nand-arm.cpio.xz;unpack=0 \
    file://findkerneldevice.sh \
    ${KERNEL_PATCH_MISC_BUFFER_SIZE} \
    ${KERNEL_PATCH_MISC_MODULES_UNUSED} \
    ${KERNEL_PATCH_MISC_MODULE_INIT} \
    ${KERNEL_PATCH_FIX_ATTRIBUTES_GCC9} \
    file://block2mtd.patch \
"

SRC_URI:append:h9 = " \
	${KERNEL_PATCH_BRCM_MMC_18V} \
"
SRC_URI:append:h9se = " \
	${KERNEL_PATCH_BRCM_MMC_18V} \
"
SRC_URI:append:i55plus = " \
	${KERNEL_PATCH_BRCM_MMC_18V} \
"
SRC_URI:append:i55se = " \
	${KERNEL_PATCH_BRCM_MMC_18V} \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image:h9 = " "
FILES:${KERNEL_PACKAGE_NAME}-image:i5plus = " "
FILES:${KERNEL_PACKAGE_NAME}-image:hzero = " "
FILES:${KERNEL_PACKAGE_NAME}-image:h8 = " "
FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
	install -m 0644 ${UNPACKDIR}/initramfs-nand-arm.cpio.xz ${B}/
}

kernel_do_install:append:h9se() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_install:append:h9combo() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_install:append:h9combose() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_install:append:h10() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_install:append:i55se() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image:h9() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

pkg_postinst:kernel-image:i55plus() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

pkg_postinst:kernel-image:hzero() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

pkg_postinst:kernel-image:h8() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

pkg_postinst:kernel-image() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			/${KERNEL_IMAGEDEST}/./findkerneldevice.sh
			dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
		fi
	fi
	true
}

do_rm_work() {
}
