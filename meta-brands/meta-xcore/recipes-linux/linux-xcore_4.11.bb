SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r0"
SRC = "20170501"

KERNEL_RELEASE = "4.11"

COMPATIBLE_MACHINE = "^(xc7346|xc7362)$"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "314b8c61217557f05ea2678313af8a9e"
SRC_URI[sha256sum] = "b3131a4de50892127eecbeaf869ac5d31e8602473b9cd214c515050ea6825f6e"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-brcmstb-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = ".6"

SRC_URI += "https://source.mynonpublic.com/xcore/xcore-linux-${PV}-${SRC}.tar.gz \
    file://defconfig \
    file://noforce_correct_pointer_usage.patch \
    file://TBS-fixes-for-4.11-kernel.patch \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', 'file://findkerneldevice.py', '', d)} \
    file://0001-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0002-makefile-disable-warnings.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://fix-never-be-null_outside-array-bounds-gcc-12.patch \
    file://use-address-of-operator-on-section-symbols-gcc-12.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

S = "${WORKDIR}/linux-brcmstb-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*  ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', '${KERNEL_IMAGEDEST}/findkerneldevice.py', '', d)}"

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ] ; then
            if grep -q 'root=/dev/mmcblk' /proc/cmdline ; then
                ${PYTHON_PN} /${KERNEL_IMAGEDEST}/findkerneldevice.py
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
