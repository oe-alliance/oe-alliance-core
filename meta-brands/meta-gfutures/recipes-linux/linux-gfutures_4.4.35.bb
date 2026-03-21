DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
VER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

KERNEL_RELEASE = "4.4.35"

SRCDATE = "20200219"

COMPATIBLE_MACHINE = "^(hd60|hd61|hd66se)$"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = "19"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "f9e67e2d0ceab518510413f8f4315bc3"
SRC_URI[kernel.ha256sum] = "45ae717b966a74326fd7297d81b3a17fd5b3962b7704170682a615ca7cdec644"

SRC_URI = "https://source.mynonpublic.com/gfutures/linux-${PV}-${SRCDATE}-${ARCH}.tar.gz;name=kernel \
	file://defconfig \
	${KERNEL_PATCHES_SERIES_4_4_35} \
	file://initramfs-subdirboot.cpio.gz;unpack=0 \
	file://findkerneldevice.sh \
	${KERNEL_PATCH_MISC_BUFFER_SIZE} \
	${KERNEL_PATCH_MISC_MODULES_UNUSED} \
	${KERNEL_PATCH_MISC_MODULE_INIT} \
	${KERNEL_PATCH_FIX_ATTRIBUTES_GCC9} \
	"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
                    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
                    ${KERNEL_PATCH_MISC_WG_KCONFIG} \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image:hd41 = " "
FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure:prepend() {
    install -d ${B}/usr
    install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append:hd41() {
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image:hd41() {
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
			dd if=${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
		fi
	fi
	true
}

do_rm_work() {
}
