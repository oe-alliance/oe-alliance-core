SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.0.1"

SRC_URI[md5sum] = "c274792d088cd7bbfe7fe5a76bd798d8"
SRC_URI[sha256sum] = "6fd63aedd69b3b3b28554cabf71a9efcf05f10758db3d5b99cfb0580e3cde24c"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".2"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://downloads.mutant-digital.net/linux-${PV}.tar.gz \
	file://defconfig \
	file://add-dmx-source-timecode.patch \
	file://iosched-slice_idle-1.patch \
	file://kernel-add-support-for-gcc6.patch \
	file://0001-Support-TBS-USB-drivers-for-4.0.1-kernel.patch \
	file://0001-TBS-fixes-for-4.0.1-kernel.patch \
	file://0001-STV-Add-PLS-support.patch \
	file://0001-STV-Add-SNR-Signal-report-parameters.patch \
	file://blindscan2.patch \
	file://0001-stv090x-optimized-TS-sync-control.patch \
	"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd1 0 0
            nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
