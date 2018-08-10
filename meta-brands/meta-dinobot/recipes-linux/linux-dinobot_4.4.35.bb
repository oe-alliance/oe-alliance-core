DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.4.35"
SRCDATE = "20180502"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".15"

SRC_URI[md5sum] = "bd22f82d08a5feb4f1360d5739919ee0"
SRC_URI[sha256sum] = "df83207ddfe34ac41a55e5e42eaae9c3ac3c4ef0750c786886719a33bf08b617"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://sdio-platform.patch \
    file://accelmem.patch \
    file://cma.patch \
    ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'file://led.patch' , '', d)} \
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