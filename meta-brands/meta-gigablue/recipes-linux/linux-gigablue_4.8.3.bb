SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

inherit kernel machine_kernel_pr

KERNEL_RELEASE = "4.8.3"

COMPATIBLE_MACHINE = "^(gb7356|gb7358|gb7362|gb73625)$"

SRC_URI[md5sum] = "39e7cdac18f40870e5f3de0e94bbac1f"
SRC_URI[sha256sum] = "db6dbddb0f3fe757c1bcc4685b56f3aaf83c7c07ecd9e4c6a994226406b82a0d"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = "r4"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/gigablue/linux/gigablue-linux-${PV}-mips-20170302.tgz \
    file://defconfig \
    file://0001-genet1-1000mbit.patch \
    file://bcmgenet_phyaddr.patch \
    file://noforce_correct_pointer_usage.patch \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://fix-never-be-null_outside-array-bounds-gcc-12.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

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
