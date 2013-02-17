DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "2.6.37"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = ""
MACHINE_KERNEL_PR_append = ".2"

SRC_URI[md5sum] = "dd7df66b5a28bd30c11fcd473a0169cf"
SRC_URI[sha256sum] = "bb1bc54ebafae37b384480f590ef201457b7ef24b064ee3477de80e257e05ff8"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archive.vuplus.com/download/kernel/stblinux-${KV}-3.1.tar.bz2 \
	file://bmips-no-array-bounds.patch \
	file://dvb-core.patch \
	file://fix_cpu_proc.patch \
	file://brcm_disable_enet1.patch \
	file://bcmgenet_oobctrl.patch \
	file://brcm_mtd_mac.patch \
	file://nfs-max-rwsize-8k.patch \
	file://defconfig \
	"

S = "${WORKDIR}/stblinux-${KV}"

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
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase  /dev/mtd2 0 0
			nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}
