DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.12.4"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".2"

SRCDATE = "16092014"
SRCDATE_ebox7358 = "17072014"

SRC_URI[ebox5100.md5sum] = "69b61da6b1fd84118cdfed642eaee15e"
SRC_URI[ebox5100.sha256sum] = "944e222090f6a1cb01c1d708bf05c792d1a905472bafd4dc957a1f15668e27d3"
SRC_URI[eboxlumi.md5sum] = "69b61da6b1fd84118cdfed642eaee15e"
SRC_URI[eboxlumi.sha256sum] = "944e222090f6a1cb01c1d708bf05c792d1a905472bafd4dc957a1f15668e27d3"
SRC_URI[ebox5000.md5sum] = "69b61da6b1fd84118cdfed642eaee15e"
SRC_URI[ebox5000.sha256sum] = "944e222090f6a1cb01c1d708bf05c792d1a905472bafd4dc957a1f15668e27d3"
SRC_URI[ebox7358.md5sum] = "62e9de9bf928f70a2d7d8bb2c4ae2127"
SRC_URI[ebox7358.sha256sum] = "bd2bb7854e0bb8c5d1ba583d949b167214ff3fb96aa8b4554026aa6ab0fd7ce8"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://source.mynonpublic.com/ebox/${MACHINE}-linux-${KV}_${SRCDATE}.tar.xz;name=${MACHINE} \
    file://defconfig \
    file://linux-3.12.4-gcc-4.9.3-build-error-fixed.patch \
    file://kernel-add-support-for-gcc-5.patch \
    file://rtl8712-fix-warnings.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://0001-Support-TBS-USB-drivers.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
    "

S = "${WORKDIR}/linux-${KV}"
B = "${WORKDIR}/build"


export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

EXTRA_OEMAKE = "TOPDIR=${STAGING_KERNEL_DIR}"

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
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

