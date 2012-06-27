DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"

SRCREV = "r2"
KV = "2.6.18-7.3"
PV = "2.6.18"
SRCDATE = "20120523"

MACHINE_KERNEL_PR_append = ".0"

MODULE = "stblinux-2.6.18"

SRC_URI = "http://en2.ath.cx/release/images/iqon/dev/stblinux-2.6.18-tmtwin-oe-${SRCDATE}.tar.gz \
		file://${MACHINE}_defconfig \
		"

S = "${WORKDIR}/stblinux-2.6.18-tmtwin-oe-${SRCDATE}"

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
