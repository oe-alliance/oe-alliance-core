SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"
KV = "3.9.6"

inherit machine_kernel_pr

SRCDATE = "20140904"

MACHINE_KERNEL_PR_append = ".01"

SRC_URI[md5sum] = "0cb37745787e0ff5070e14a7bbf3dc5c"
SRC_URI[sha256sum] = "018d9792c6ba00400d7779568bc096cd1df2edb8d57501d3477c4734655c6e0f"


LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.openmips.com/gigablue-linux-${PV}-${SRCDATE}.tgz \
        file://defconfig \
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
}

pkg_postinst_kernel-image_gb800se () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd2 0 0
            nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}

pkg_postinst_kernel-image_gb800ue () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd2 0 0
            nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}

pkg_postinst_kernel-image_gb800solo () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd2 0 0
            flashcp /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz /dev/mtd2
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    true
}
