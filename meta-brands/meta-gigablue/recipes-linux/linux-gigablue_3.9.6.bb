SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.9.6"

inherit kernel machine_kernel_pr

SRCDATE = "20140904"

SRC_URI[md5sum] = "0cb37745787e0ff5070e14a7bbf3dc5c"
SRC_URI[sha256sum] = "018d9792c6ba00400d7779568bc096cd1df2edb8d57501d3477c4734655c6e0f"


LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".3"

SRC_URI = "http://source.mynonpublic.com/gigablue/linux/gigablue-linux-${PV}-${SRCDATE}.tgz \
    file://defconfig \
    file://nor-maps-gb800solo.patch \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://cinergy_s2_usb_r2.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://nfs-max-rwsize-8k.patch \
    file://0001-rt2800usb-add-support-for-rt55xx.patch \
    file://linux-sata_bcm.patch \
    file://brcmnand.patch \
    file://fix_fuse_for_linux_mips_3-9.patch \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://linux-3.9-gcc-4.9.3-build-error-fixed.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    file://kernel-add-support-for-gcc-5.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://0001-Support-TBS-USB-drivers-3.9.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://0002-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0003-log2-give-up-on-gcc-constant-optimizations.patch \
        "

S = "${WORKDIR}/linux-${KV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
}

pkg_postinst_kernel-image_gb800se () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}

pkg_postinst_kernel-image_gb800ue () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}

pkg_postinst_kernel-image_gb800solo () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            flashcp /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz /dev/${MTD_KERNEL}
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install

