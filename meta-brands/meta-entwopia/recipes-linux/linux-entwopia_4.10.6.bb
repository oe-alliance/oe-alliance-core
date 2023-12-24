SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.10.6"
SRCDATE = "20170831"

COMPATIBLE_MACHINE = "^(ch625dt|ch625lc|yh625dt|yh625tc|yh73625)$"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "cb28a0056279ad46af776fd5cdbcf88f"
SRC_URI[sha256sum] = "a147b639445206ce85a56e155667ada38db1512e1c78fe9d4fd6d4be6f5a2e0e"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}-base/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = "3"

RPROVIDES:kernel-image = "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/entwopia/${MACHINE}/${MACHINE}-linux-${PV}-base-${SRCDATE}.tgz \
    file://defconfig \
    file://0001-add-dmx-source-timecode.patch \
    file://0002-nand-ecc-strength-and-bitflip.patch \
    file://TBS-fixes-for-4.10-kernel.patch \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://0001-revert-xhci-plat.patch \
    file://noforce_correct_pointer_usage.patch \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://add-more-devices-rtl8xxxu.patch \
    file://0005-xbox-one-tuner-4.10.patch \
    file://0006-dvb-media-tda18250-support-for-new-silicon-tuner.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://fix-never-be-null_outside-array-bounds-gcc-12.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

S = "${WORKDIR}/linux-${PV}-base"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_install:append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
