SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
SRC = "20171113"

inherit kernel machine_kernel_pr

KERNEL_RELEASE = "4.9.51"

SRC_URI[md5sum] = "691774d3778f67064c46da1959526d7d"
SRC_URI[sha256sum] = "91a363188077ddbae2337910ea070c9e7a54d2cf3a4793507706d4b469c80b0d"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".0"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/xtrend/linux-${PV}-${SRC}.tar.xz \
    file://defconfig \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install_append() {
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

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p3
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm_kernel-image () {
}

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-etxx00-${KV}:"

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
