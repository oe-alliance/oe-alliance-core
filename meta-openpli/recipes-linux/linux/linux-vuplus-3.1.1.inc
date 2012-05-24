DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.1.1"

SRCREV = "r2"

SRC_URI[md5sum] = "4dc3ac322453abbfaade7020cddea205"
SRC_URI[sha256sum] = "1d18eb39677a23eace6b27ee25656c25f21b57be7e77a2adcdd15c76d1c3e875"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-vuplus-${KV}:"

SRC_URI += "http://archive.vuplus.com/download/kernel/linux-${KV}_${SRCREV}.tar.bz2 \
	file://fix_cpu_proc.patch;striplevel=1 \
	file://iosched-slice_idle-1.patch \
        file://${MACHINE}_defconfig \
        "

S = "${WORKDIR}/linux-${KV}"

inherit kernel


export OS = "Linux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "vmlinux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"



do_configure_prepend() {
        oe_machinstall -m 0644 ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
        oe_runmake oldconfig
}

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
        gzip ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
}

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                flash_eraseall -j /dev/mtd1
                nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        true
}

