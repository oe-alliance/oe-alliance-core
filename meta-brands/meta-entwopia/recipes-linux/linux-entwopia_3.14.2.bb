SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "3.14.2"
SRCDATE_ew7356 = "20141118"
SRCDATE_ew7358 = "20141102"
SRCDATE_ew7362 = "20140517"
SRCDATE_ch62lc = "20150920"

inherit kernel machine_kernel_pr

SRC_URI[ew7356.md5sum] = "15f56eb97fcc3512b031fffec7046f99"
SRC_URI[ew7356.sha256sum] = "060093585e4de86cf5c1f717d1f6fca486a633675e94fa5a07fab566ec074e33"
SRC_URI[ew7358.md5sum] = "e0d617b82d4efe455ee5b1f56c0c2155"
SRC_URI[ew7358.sha256sum] = "b3480496dd888b6a0fce0202cd424e54a3868555edd56d050861e0c9439e843c"
SRC_URI[ew7362.md5sum] = "23781bce0ab453e7c18092f1f6dd2e2b"
SRC_URI[ew7362.sha256sum] = "bf31e5b1c6e9295bdcd531b94a0f327f70b07bc901cd68dbc1730f04274a86d4"
SRC_URI[ch62lc.md5sum] = "23781bce0ab453e7c18092f1f6dd2e2b"
SRC_URI[ch62lc.sha256sum] = "bf31e5b1c6e9295bdcd531b94a0f327f70b07bc901cd68dbc1730f04274a86d4"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}-base/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/entwopia/${MACHINE}/${MACHINE}-linux-${PV}-base-${SRCDATE}.tgz;name=${MACHINE} \
	file://defconfig \
	file://add-rt2x00-wifi-devices.patch \
	file://add-rtl8192cu-wifi-devices.patch \
	file://add-dmx-source-timecode.patch \
	file://af9015-output-full-range-SNR.patch \
	file://af9033-output-full-range-SNR.patch \
	file://cxd2820r-output-full-range-SNR.patch \
	file://dvb_usb_disable_rc_polling.patch \
	file://dvb-usb-dib0700-disable-sleep.patch \
	file://fix-proc-cputype.patch \
	file://iosched-slice_idle-1.patch \
	file://it913x-switch-off-PID-filter-by-default.patch \
	file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
	file://tda18271-advertise-supported-delsys.patch \
	file://timedate.patch \
	"

S = "${WORKDIR}/linux-${PV}-base"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_install_append() {
	${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/mtd2 0 0
			nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}

do_rm_work() {
}