DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.12.4"
PR = "r3"

SRCDATE = "1211"
SRCDATE_ebox7358 = "17072014"
MACHINE_KERNEL_PR_append = ".5"

SRC_URI[ebox5100.md5sum] = "2c6957a0f3662910e53df8eb31705e01"
SRC_URI[ebox5100.sha256sum] = "2ba88a551a5c3a7095a2000e03841895d7b3892116d379d1009a8de9f5820f89"
SRC_URI[eboxlumi.md5sum] = "2c6957a0f3662910e53df8eb31705e01"
SRC_URI[eboxlumi.sha256sum] = "2ba88a551a5c3a7095a2000e03841895d7b3892116d379d1009a8de9f5820f89"
SRC_URI[ebox5000.md5sum] = "bafb17f5e8dd1c4935d2ec213b2d62e1"
SRC_URI[ebox5000.sha256sum] = "36b844b07e5f38b3b597bc55bd182438f9fd3b3c52d234914f6aabf031e31be9"
SRC_URI[ebox7358.md5sum] = "62e9de9bf928f70a2d7d8bb2c4ae2127"
SRC_URI[ebox7358.sha256sum] = "bd2bb7854e0bb8c5d1ba583d949b167214ff3fb96aa8b4554026aa6ab0fd7ce8"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.mixos-support.com/${MACHINE}-linux-${KV}_${SRCDATE}.tar.bz2;name=${MACHINE} \
    file://defconfig \
    "
	
SRC_URI_ebox7358 = "http://archiv.mixos-support.com/${MACHINE}-linux-${KV}_${SRCDATE}.tar.xz;name=${MACHINE} \
    file://defconfig \
    "

S = "${WORKDIR}/linux-${KV}"

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
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}


