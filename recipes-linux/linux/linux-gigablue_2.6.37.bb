DESCRIPTION = "Linux kernel for ${MACHINE}"
DESCRIPTION = "Linux kernel for Gigablue HD ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"

SRCDATE = "20120529"
MACHINE_KERNEL_PR_append = ".9"

SRC_URI[md5sum] = "0ad9128167c2bd68e0228843f49f4090"
SRC_URI[sha256sum] = "47a797ef5502019b3d48f22ef745e69c968629991c5bbf108cfe18d7268a7ec6"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-linux-${PV}_${SRCDATE}.tgz \
	file://defconfig \
	file://memory-gcc464-fix.patch \
	file://signal.c.patch \
	"

S = "${WORKDIR}/linux-${PV}"

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
			flash_erase /dev/mtd2 0 0
			nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	true
}
