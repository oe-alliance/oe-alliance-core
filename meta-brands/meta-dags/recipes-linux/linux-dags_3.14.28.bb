SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"

MACHINE_KERNEL_PR_append = ".5"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "c94fc213c29459be47175e6959a15827"
SRC_URI[sha256sum] = "bc3a8ac32b7a861dc41e1cda927fa7339313fdc13588120100751b14c40417e0"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://en3homeftp.net/pub/down/linux-3.14.28.tar.xz \
	file://defconfig \
	file://date-time.patch \
	file://rtl8712-fix-warnings.patch \
	"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"


FILES_kernel-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p1
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

do_rm_work() {
}
