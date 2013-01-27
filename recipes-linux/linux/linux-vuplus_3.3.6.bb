DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.3.6"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = ""
MACHINE_KERNEL_PR_append = ".13"

SRC_URI[md5sum] = "64bc9b526a668d6cad1ea83646137026"
SRC_URI[sha256sum] = "da99dc00ad47696a7cf1f56fc90e42b51f3dbd2982a6f11a2ac5dc2d12f22b4f"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archive.vuplus.com/download/kernel/stblinux-${KV}-1.2.tar.bz2 \
	file://bmips-no-array-bounds.patch \
	file://brcm_3.3.patch \
	file://fix_cpu_proc.patch \
	file://brcm_mtd_mac.patch \
	file://dvb_core_5.5.patch \
	file://brcm_remove_entire_mtd.patch;patch=1;pnum=1 \
	file://defconfig \
	\
	file://dvb-usb-af9035.patch \
	file://dvb-usb-a867.patch \
	file://dvb-usb-rtl2832.patch \
	file://cxd2820r-enable-LNA-for-DVB-T.patch \
	file://cxd2820r-changed-condition-to-break-out-from-wait-lock-loop.patch \
	file://cxd2820r-output-full-range-SNR.patch \
	file://cinergy_s2_usb_r2.patch \
	file://as102-scale-MER-to-full-range.patch \
	file://as102-adjust-signal-strength-report.patch \
	file://em28xx-pre-allocate-DVB-isoc-transfer-buffers.patch \
	file://em28xx-dvb-stop-URBs-when-stopping-the-streaming.patch \
	file://af9015-output-full-range-SNR.patch \
	file://it913x-backports-from-kernel-3.4.patch \
	file://it913x-backports-from-kernel-3.5.patch \
	file://it913x-switch-off-PID-filter-by-default.patch \
	file://it913x-fix-bulk-read-write-retry-loop.patch \
	file://dvb-usb-dib0700-disable-sleep.patch \
	file://dvb_usb_disable_rc_polling.patch \
	file://fix-dvb-siano-sms-order.patch \
	"

S = "${WORKDIR}/linux"

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
