SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

inherit kernel machine_kernel_pr kernel-fixups

KERNEL_RELEASE = "4.8.3"

COMPATIBLE_MACHINE = "^(gb7356|gb7358|gb7362|gb73625)$"

SRC_URI[md5sum] = "39e7cdac18f40870e5f3de0e94bbac1f"
SRC_URI[sha256sum] = "db6dbddb0f3fe757c1bcc4685b56f3aaf83c7c07ecd9e4c6a994226406b82a0d"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r5"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/gigablue/linux/gigablue-linux-${PV}-mips-20170302.tgz \
    file://defconfig \
    file://0001-genet1-1000mbit.patch \
    ${KERNEL_PATCH_BRCM_GENET_PHYADDR} \
    ${KERNEL_PATCH_FIX_NOFORCE_POINTER} \
    ${KERNEL_PATCH_TBS_USB_46} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    ${KERNEL_PATCH_FIX_LOG2} \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    ${KERNEL_PATCH_BINUTILS241_V4} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f ${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
