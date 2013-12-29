SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.9.7"
PR = "r0"

SRCDATE = "2013-1211"
MACHINE_KERNEL_PR_append = ".6"

SRC_URI[md5sum] = "42f18f5ee5e683e6345598a6f4a71531"
SRC_URI[sha256sum] = "549422fad208dff49771d9ef19c404bb30177f2023c601adb3d220c81201e19a"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}-r1/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = " http://en2.ath.cx/release/images/oedrivers/linux-${KV}-r1-${SRCDATE}.tar.gz \
    file://defconfig \
    file://nfs-max-rwsize-8k.patch \
    "

S = "${WORKDIR}/linux-${KV}-r1"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

do_configure_prepend() {
    oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
    oe_runmake oldconfig
}

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

MTD_DEVICE = "mtd6"

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
