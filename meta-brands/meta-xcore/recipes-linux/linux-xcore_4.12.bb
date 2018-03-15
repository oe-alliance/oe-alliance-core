SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r0"
SRC = "20170706"

KERNEL_RELEASE = "4.12"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "32960e787b30bf3aba7c7b46be2467c5"
SRC_URI[sha256sum] = "e8de29e61728dd4692723f13b72d3553b8d907d0119a276fc8d8341e599b8511"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-brcmstb-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".2"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/xcore/xcore-linux-${PV}-${SRC}.tar.gz \
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
    "

S = "${WORKDIR}/linux-brcmstb-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES_${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*  ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', '${KERNEL_IMAGEDEST}/findkerneldevice.py', '', d)}"

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ] ; then
            if grep -q 'root=/dev/mmcblk' /proc/cmdline ; then
                python /${KERNEL_IMAGEDEST}/findkerneldevice.py
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

pkg_postrm_kernel-image () {
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
