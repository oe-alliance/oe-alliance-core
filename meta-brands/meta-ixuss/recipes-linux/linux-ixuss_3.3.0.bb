SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

inherit kernel machine_kernel_pr

KERNEL_RELEASE = "3.3.0"

SRC_URI[md5sum] = "a2fbd9e424a8d0d6157fcb25e08ec393"
SRC_URI[sha256sum] = "5ad462c4a9b8433685eef816090cb9fbc83a88126ea1fc5c0495ad79bf849469"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/stblinux-3.3.0-20130404.tgz \
    file://defconfig \
    file://0001-kernel-add-support-for-gcc-5.patch \
    file://fix-proc-cputype.patch \
    file://fixme-hardfloat.patch \
    file://0001-Revert-MIPS-Add-fast-get_user_pages.patch \
    file://disable_early_fb.patch \
    file://iosched-slice_idle-1.patch \
    file://mips-refactor-clearpage-and-copypage_311.patch \
    file://add-dmx-source-timecode.patch \
    file://rtl8712-release-firmware-fix.patch \
    file://dvb-usb-af9035.patch \
    file://tda18218-7mhz-lopass.patch \
    file://dvb-usb-a867.patch \
    file://dvb-usb-rtl2832.patch \
    file://cxd2820r-enable-LNA-for-DVB-T.patch \
    file://cxd2820r-changed-condition-to-break-out-from-wait-lock-loop.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://cinergy_s2_usb_r2.patch \
    file://as102-backports-from-kernel-3.4.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://em28xx-pre-allocate-DVB-isoc-transfer-buffers.patch \
    file://em28xx-dvb-stop-URBs-when-stopping-the-streaming.patch \
    file://af9015-output-full-range-SNR.patch \
    file://it913x-backports-from-kernel-3.4.patch \
    file://it913x-backports-from-kernel-3.5.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://it913x-fix-bulk-read-write-retry-loop.patch \
    file://tda10071-BUGFIX-delivery-system.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://nfs-max-rwsize-8k.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://dvb_frontend-Multistream-support-3.3.patch \
    file://timeconst_perl5.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://kernel-add-support-for-gcc7.patch \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
    rm -rf ${STAGING_KERNEL_DIR}/.cofig
    rm -rf ${STAGING_KERNEL_DIR}/.config
    rm -rf ${STAGING_KERNEL_DIR}/.config.old
    rm -rf ${STAGING_KERNEL_DIR}/include/generated
    rm -rf ${STAGING_KERNEL_DIR}/include/config
    rm -rf ${STAGING_KERNEL_DIR}/arch/mips/include/generated
}


kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
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

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
