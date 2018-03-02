DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.4.35"
SRCDATE_u5pvr = "20171018"
SRCDATE_u5 = "20180301"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".9"

SRC_URI[u5pvr.md5sum] = "5b78e7c0fb860f2a707de6a437a097e9"
SRC_URI[u5pvr.sha256sum] = "16d4841a6ab678fdf60162b41466675ca6e2e9af38b4722bdd9571b36969f9d2"
SRC_URI[u5.md5sum] = "6c1550bdbcb8fda764fdfd558a8b276e"
SRC_URI[u5.sha256sum] = "eec7fa5e2238abee68b323b38894a2f9e9bad8dfbec6e8de9500b18a244a42cf"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz;name=${MACHINE} \
    file://defconfig \
    file://0001-Support-TBS-USB-drivers-for-4.3-kernel.patch \
    file://0001-TBS-fixes-for-4.3-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
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
	if [ -e "${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko" ]; then
		rm -f ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko
	fi
}

do_rm_work() {
}