SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.13.5"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".4"

SRCREV = ""

SRC_URI[md5sum] = "19e9956653437b99b4fa6ec3e16a3e99"
SRC_URI[sha256sum] = "ef7fb307582ff243aacff8a13025fe028634aaf650ada309991ae03622962f61"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

KERNEL_CONFIG = "${@base_contains("MACHINE_FEATURES", "dvbproxy", "defconfig_proxy", "defconfig", d)}"

SRC_URI = "http://archive.vuplus.com/download/kernel/stblinux-${KV}.tar.bz2 \
    file://${KERNEL_CONFIG} \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://linux-tcp_output.patch \
    file://linux-3.13-gcc-4.9.3-build-error-fixed.patch \
    file://kernel-add-support-for-gcc-5.patch \
    file://rtl8712-fix-warnings.patch \
    file://0001-Support-TBS-USB-drivers-3.13.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
    ${@base_contains("MACHINE_FEATURES", "dvbproxy", "file://linux_dvb_adapter.patch;patch=1;pnum=1", "", d)} \
    file://brcm_remove_entire_mtd.patch \
    "

SRC_URI_append_vuduo2 = "file://brcm_s3_wol.patch;patch=1;pnum=1 "
SRC_URI_append_vusolose = "file://brcm_s3_wol.patch;patch=1;pnum=1 \
                          file://linux_mtd_bbt_maxblock.patch \
"
SRC_URI_append_vusolo2 = "file://linux-bcm_ethernet.patch;patch=1;pnum=1 "
 
S = "${WORKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
    if [ -e ${WORKDIR}/defconfig_proxy ]; then
    mv ${WORKDIR}/defconfig_proxy ${WORKDIR}/defconfig
    fi
}

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
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

do_rm_work() {
}