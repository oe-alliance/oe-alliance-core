SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"
KV = "3.3.8"
PR = "r2"

inherit machine_kernel_pr

MACHINE_KERNEL_PR_append = ".5"

SRC_URI[md5sum] = "dbd9b0dcf632d52b5765a9d7372e0205"
SRC_URI[sha256sum] = "6922bb05875664d54fa653fb27b4ea91fb28009fc6122d760e601429c43e69af"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-3.3.8-2.0/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.openmips.com/linux-gbquad-3.3.8-2.0_20130716.tar.gz \
    file://defconfig \
    file://dvb_core_5.5.patch;patch=1;pnum=1 \
    file://bmips-no-array-bounds.patch;patch=1;pnum=1 \
    file://mips-refactor-clearpage-and-copypage.patch \
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
    file://nfs-max-rwsize-8k.patch \
    file://linux-3.3.8-dvbsky.patch \
    "
	
SRC_URI_append_gbquadplus = " file://brcm_s3_wol.patch"

S = "${WORKDIR}/linux-3.3.8-2.0"

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
