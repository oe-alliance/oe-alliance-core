SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r13"

inherit machine_kernel_pr

KV = "3.9.7"
SRCDATE = "20140311"

SRC_URI[md5sum] = "4dce3f8fe28f8c4a3ba5c26c4183ba51"
SRC_URI[sha256sum] = "f5a1db026e7850c1cfb6a76a3e775ef665ad9545ea9a22f7290f7bfb42371c51"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".26"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://pluginvalley.kr/bcm/kernel/linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://nfs-max-rwsize-8k.patch \
    "
	
S = "${WORKDIR}/linux-${PV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
    oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
    oe_runmake oldconfig
}

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

MTD_DEVICE_sogno8800hd = "mtd8"
MTD_DEVICE_uniboxhde = "mtd8"

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_DEVICE} 0 0
            nandwrite -p /dev/${MTD_DEVICE} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}
