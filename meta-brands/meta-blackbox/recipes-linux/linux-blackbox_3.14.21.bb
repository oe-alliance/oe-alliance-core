SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PR = "r1"

inherit machine_kernel_pr

KV = "3.14.21"
SRCDATE = "20150217"

SRC_URI[md5sum] = "ec97d409ce4b29fa26ffc375206cd760"
SRC_URI[sha256sum] = "66d8c746c4f871e5f62b6de4c3875e62e92cc40ce2f8626e32adae1e1d78f598"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".9"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://unibox.to/feeds/eco/development/kernel/linux-${KV}-${SRCDATE}.tar.gz \
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

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd7 0 0
            nandwrite -p /dev/mtd7 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}
