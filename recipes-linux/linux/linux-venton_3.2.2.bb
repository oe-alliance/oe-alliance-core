DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"
KV = "3.2.2"

SRCDATE = "20120228"
MACHINE_KERNEL_PR_append = ".0"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "87e2da21128620725e31a6aee2878cb8"
SRC_URI[sha256sum] = "d389252264369c9485689a99166881951040c2376afb9c3f44481f06748587b1"

PV = "${KV}-${SRCDATE}"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-venton-${KV}:"

SRC_URI += "http://code-ini.com/software/kernel/linux-${KV}-${SRCDATE}.tar.gz \
	file://defconfig \
	file://fix-proc-cputype.patch \
	file://iosched-slice_idle-1.patch \
	file://add-dmx-source-timecode.patch \
	file://dvb-usb-af9035.patch \
	file://tda18218-7mhz-lopass.patch \
	file://dvb-usb-a867.patch \
	file://cxd2820r-enable-LNA-for-DVB-T.patch \
	file://cxd2820r-changed-condition-to-break-out-from-wait-lock-loop.patch \
	file://cxd2820r-output-full-range-SNR.patch \
	file://dvb-usb-smsdvb_fix_frontend.patch \
	file://dvb-usb-rtl2832.patch \
	file://xc3028-fix-center-frequency.patch \
	file://cinergy_s2_usb_r2.patch \
	file://af9015-output-full-range-SNR.patch \
	file://dvb-as102-driver-updates.patch \
	file://dvb-usb-it913x-patch-collection.patch \
	file://em28xx-reworked-device-probing-to-get-max-dvb-iso-packet-size.patch \
	file://em28xx-pre-allocate-DVB-iso-transfer-buffers.patch \
	"

S = "${WORKDIR}/linux-${KV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
	oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
	oe_runmake oldconfig
}

kernel_do_install_append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	gzip ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase  /dev/mtd1 0 0
			nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}
 
