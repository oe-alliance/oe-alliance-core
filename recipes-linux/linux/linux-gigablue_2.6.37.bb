DESCRIPTION = "Linux kernel for ${MACHINE}"
DESCRIPTION = "Linux kernel for Gigablue HD ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"

SRCDATE = "20120611"
MACHINE_KERNEL_PR_append = ".0"

SRC_URI[md5sum] = "c0e884e5ba38ce0e499577995fcd1ca2"
SRC_URI[sha256sum] = "0b89270b2d2b7f9649852290eba968770bd998d0ff675b45dce59afed1d52cc1"

LIC_FILES_CHKSUM = "file://${WORKDIR}/stblinux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-linux-${PV}_${SRCDATE}.tar.bz2 \
	file://defconfig \
	"

S = "${WORKDIR}/stblinux-${PV}"

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
			flash_erase /dev/mtd2 0 0
			nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	true
}
