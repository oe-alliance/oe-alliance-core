SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r0"
SRC = "20170501"

KERNEL_RELEASE = "4.11"

COMPATIBLE_MACHINE = "^(xc7346|xc7362)$"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[md5sum] = "314b8c61217557f05ea2678313af8a9e"
SRC_URI[sha256sum] = "b3131a4de50892127eecbeaf869ac5d31e8602473b9cd214c515050ea6825f6e"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-brcmstb-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r7"

SRC_URI += "https://source.mynonpublic.com/xcore/xcore-linux-${PV}-${SRC}.tar.gz \
    file://defconfig \
    ${KERNEL_PATCH_FIX_NOFORCE_POINTER} \
    file://TBS-fixes-for-4.11-kernel.patch \
    ${KERNEL_PATCH_TBS_USB_46} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://findkerneldevice.py \
    file://0001-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0002-makefile-disable-warnings.patch \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    file://use-address-of-operator-on-section-symbols-gcc-12.patch \
    ${KERNEL_PATCH_BINUTILS241_V5} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-brcmstb-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}* /${KERNEL_IMAGEDEST}/findkerneldevice.py"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ] ; then
            if grep -q 'root=/dev/mmcblk' /proc/cmdline ; then
                python3 /${KERNEL_IMAGEDEST}/findkerneldevice.py
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
            else
                flash_erase /dev/${MTD_KERNEL} 0 0
                nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
                rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
            fi
        fi
    fi
    true
}

pkg_postrm:kernel-image () {
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
