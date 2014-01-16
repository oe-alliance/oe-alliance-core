DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.5.3"

SRCDATE = "20131210"
MACHINE_KERNEL_PR_append = ".2"

SRC_URI[md5sum] = "196c304f09aebc08c14c9be55426a3e8"
SRC_URI[sha256sum] = "e189b052fbca34e616d669d0125d6d49cf4c3ce0ea3fcf690187a27ad6fec5a4"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RCONFLICTS_${PN} = "linux-technomate"
RREPLACES_${PN} = "linux-technomate"

SRC_URI = "http://en2.ath.cx/release/images/oedrivers/linux-${KV}-tm-${SRCDATE}.tar.gz \
		file://nfs-max-rwsize-8k.patch \
		file://001_fix_standby_error.patch \
		file://dvb-usb-a867.patch \
		file://dvb-usb-rtl2832.patch \
		file://defconfig \
		"

SRC_URI_append_tmnano = " \
		file://002_fix_partitionmap.patch \
		file://003_fix_not_has_nor.patch \
		file://004_fix_bootarg.patch \
		"

SRC_URI_append_tmnano2t = " \
		file://002_fix_partitionmap.patch \
		file://003_fix_not_has_nor.patch \
		file://004_fix_bootarg.patch \
		"

SRC_URI_append_optimussos1 = " \
		file://002_fix_partitionmap.patch \
		file://003_fix_not_has_nor.patch \
		file://004_fix_bootarg.patch \
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

MTD_DEVICE = "mtd6"
MTD_DEVICE_tmnano = "mtd1"

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
