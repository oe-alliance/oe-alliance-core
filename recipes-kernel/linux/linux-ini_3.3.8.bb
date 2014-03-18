SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit machine_kernel_pr

KV = "3.3.8"
SRCDATE = "20130829"

SRC_URI[md5sum] = "5abe103441c68b09132d807ee3668cb4"
SRC_URI[sha256sum] = "5d94250af0afac4fdf8ddb7b9bf3dd0a0df9d2e1256935348703117f200f6114"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".3"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI = "http://code-ini.com/software/kernel/linux-${KV}-${SRCDATE}.tar.bz2 \
    file://brcm_3.3.patch;patch=1;pnum=1 \
    file://fix_cpu_proc.patch;patch=1;pnum=1 \
    file://brcm_mtd_mac.patch;patch=1;pnum=1 \
    file://dvb_core_5.5.patch;patch=1;pnum=1 \
    file://bmips-no-array-bounds.patch;patch=1;pnum=1 \
    file://mips-refactor-clearpage-and-copypage.patch \
    file://defconfig \
    file://nand_base.patch \
    file://brcm_s3_wol.patch;patch=1;pnum=1 \
    file://remove_genet1.patch;patch=1;pnum=1 \
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
    file://it913x-enable-endpoint-3-on-devices-with-HID-interface.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://nfs-max-rwsize-8k.patch \
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

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}
