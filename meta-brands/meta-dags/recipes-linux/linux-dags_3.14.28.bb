SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"

KV = "3.14.28"
MACHINE_KERNEL_PR_append = ".7"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "d83e48d38bf83c50f7a175bc6a1fd2ce"
SRC_URI[sha256sum] = "9c04354eba2861304d09bce015b4d381c65f968ba445465a70a77bee3ee16f45"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://en3homeftp.net/pub/src/linux-3.14.28.tar.xz \
	file://defconfig \
	file://date-time.patch \
	file://rtl8712-fix-warnings.patch \
	file://0001.fix_hwtype.patch \
	file://0002.recording_issue.patch \
	file://0001.remove_vtuner_index_check.patch \
	file://kernel-gcc6.patch \
	"

S = "${WORKDIR}/linux"
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
