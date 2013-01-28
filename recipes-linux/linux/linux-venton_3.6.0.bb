DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

KV = "3.6.0"
SRCDATE = "20130118"

SRC_URI[md5sum] = "f28c5166dce46e4b5a939718e58f2b22"
SRC_URI[sha256sum] = "a6d387dcebe3eaafe8ccdb80deb848f65610e6c13fcea8e3ef6087d4b284c5c0"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".14"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://code-ini.com/software/kernel/linux-${KV}-${SRCDATE}.tar.gz \
	file://defconfig \
	file://0001-Revert-MIPS-mm-Add-compound-tail-page-_mapcount-when.patch \
	file://0001-Revert-MIPS-Add-fast-get_user_pages.patch \
	file://add-dmx-source-timecode.patch \
	file://af9015-output-full-range-SNR.patch \
	file://af9033-output-full-range-SNR.patch \
	file://as102-adjust-signal-strength-report.patch \
	file://as102-scale-MER-to-full-range.patch \
	file://cinergy_s2_usb_r2.patch \
	file://cxd2820r-output-full-range-SNR.patch \
	file://dvb-usb-a867.patch \
	file://dvb-usb-dib0700-disable-sleep.patch \
	file://dvb-usb-rtl2832.patch \
	file://dvb_usb_disable_rc_polling.patch \
	file://em28xx_add_terratec_h5_rev3.patch \
	file://fix-proc-cputype.patch \
	file://iosched-slice_idle-1.patch \
	file://it913x-switch-off-PID-filter-by-default.patch \
	file://tda18271-advertise-supported-delsys.patch \
	file://fix-dvb-siano-sms-order.patch \
	file://linux-3.6.0-dvbsky.patch \
	file://nfs-max-rwsize-8k.patch \
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

MTD_DEVICE_ventonhdx = "mtd1"

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
