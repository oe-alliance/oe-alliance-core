DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.4.35"
SRCDATE = "20180411"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".9"


SRC_URI[md5sum] = "70f7f2a5e06f1ad0742240b9039a4f12"
SRC_URI[sha256sum] = "ac98fa4d66a0d209bf4152868d7fd7c979fc009bace6946025140f2b2d493932"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/clap/clap-linux-${PV}-${SRCDATE}.tar.gz \
    file://defconfig \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

kernel_do_install_append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

do_rm_work() {
}