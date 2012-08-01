DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.4.6"

SRCDATE = "20120801"
MACHINE_KERNEL_PR_append = ".0"

SRC_URI[md5sum] = "04dcd882e3f72d12b58451dbc66b2a9b"
SRC_URI[sha256sum] = "4c08d5e82d46f1634376b34163c38af425326426bf9b1240bebd7a9c3dcd804f"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}-tm-${SRCDATE}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/linux-${KV}-tm-${SRCDATE}.tar.gz \
		file://defconfig"

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
			flash_erase /dev/mtd6 0 0
			nandwrite -p /dev/mtd6 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}
