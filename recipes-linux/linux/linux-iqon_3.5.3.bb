DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.5.3"

SRCDATE = "20130611"
MACHINE_KERNEL_PR_append = ".3"

SRC_URI[md5sum] = "d093086aaf4108879a96812f62837a0c"
SRC_URI[sha256sum] = "cd11c19ddb2ea03f03a5bcb70c46137e6689a6450d3b47bde070be68bcb1de9d"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RCONFLICTS_${PN} = "linux-technomate"
RREPLACES_${PN} = "linux-technomate"

SRC_URI = "http://en2.ath.cx/pub/OpenPLi3/src/linux-${KV}-${SRCDATE}.tar.gz \
		file://nfs-max-rwsize-8k.patch \
		file://001_fix_standby_error_${MACHINE}.patch \		
		file://defconfig \
		"

SRC_URI_append_tmnano = " \
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
