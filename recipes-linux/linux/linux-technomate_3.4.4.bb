DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.4.4"

SRCDATE = "20120711"
MACHINE_KERNEL_PR_append = ".0"

SRC_URI[md5sum] = "fe7824f1b11869ce40422b115ceaadfa"
SRC_URI[sha256sum] = "393a9cb7ccb64b79e6ea4ba1bcaec9a9d9fadb113828cfb6dcd30c23ff0ad3c4"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/linux-3.4.4-tm-${SRCDATE}.tar.gz \
		file://${MACHINE}_defconfig"

S = "${WORKDIR}/linux-${KV}-tm-${SRCDATE}"

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
	install -d ${D}${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}${KERNEL_IMAGEDEST}
	gzip ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase  /dev/mtd0 0 0
			nandwrite -p /dev/mtd0 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}
