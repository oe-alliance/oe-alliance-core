DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

KERNEL_RELEASE = "3.4.3"

SRC_URI[md5sum] = "57a986f69a0f0601b77710b4829e4b47"
SRC_URI[sha256sum] = "d81d051e6f702fc4bda0cb6b18db7319ed3b36401a924b682b0b0b94e0c23ad7"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".10"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://www.et-view.com/download/linux-${PV}.tar.gz \
	file://defconfig \
	file://fix-proc-cputype.patch \
	file://0001-Revert-MIPS-Add-fast-get_user_pages.patch \
	file://iosched-slice_idle-1.patch \
	file://add-dmx-source-timecode.patch \
	file://dvb-usb-af9035.patch \
	file://tda18218-7mhz-lopass.patch \
	file://dvb-usb-a867.patch \
	file://dvb-usb-rtl2832.patch \
	file://cxd2820r-enable-LNA-for-DVB-T.patch \
	file://cxd2820r-changed-condition-to-break-out-from-wait-lock-loop.patch \
	file://cxd2820r-output-full-range-SNR.patch \
	file://cinergy_s2_usb_r2.patch \
	file://as102-scale-MER-to-full-range.patch \
	file://as102-adjust-signal-strength-report.patch \
	file://em28xx-dvb-stop-URBs-when-stopping-the-streaming.patch \
	file://af9015-output-full-range-SNR.patch \
	file://it913x-switch-off-PID-filter-by-default.patch \
	file://it913x-fix-bulk-read-write-retry-loop.patch \
	file://dvb-usb-dib0700-disable-sleep.patch \
	file://dvb_usb_disable_rc_polling.patch \
	file://cifs-fix-parsing-of-password-mount-option.patch \
	file://em28xx_add_terratec_h5_rev3.patch \
	"

SRC_URI_append_odinm9 = " file://board.patch"

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

MTD_DEVICE_et9x00 = "mtd1"
MTD_DEVICE_et6x00 = "mtd1"
MTD_DEVICE_et5x00 = "mtd1"
MTD_DEVICE_odinm9 = "mtd2"

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
