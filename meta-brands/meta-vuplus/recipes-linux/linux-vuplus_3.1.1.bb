SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.1.1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit machine_kernel_pr

SRCREV = "r2"
MACHINE_KERNEL_PR_append = ".2"

SRC_URI[md5sum] = "4dc3ac322453abbfaade7020cddea205"
SRC_URI[sha256sum] = "1d18eb39677a23eace6b27ee25656c25f21b57be7e77a2adcdd15c76d1c3e875"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://archive.vuplus.com/download/kernel/linux-${KV}_${SRCREV}.tar.bz2 \
    file://fix_cpu_proc.patch \
    file://mips-refactor-clearpage-and-copypage_311.patch \
    file://iosched-slice_idle-1.patch \
    file://defconfig \
    file://dvb-usb-af9035.patch \
    file://dvb-usb-it9135.patch \
    file://tda18218-7mhz-lopass.patch \
    file://dvb-usb-a867.patch \
    file://PCTV-DVB-S2-stick-460e.patch \
    file://cxd2820r-enable-LNA-for-DVB-T.patch \
    file://cxd2820r-changed-condition-to-break-out-from-wait-lock-loop.patch \
    file://dvb-usb-smsdvb_fix_frontend.patch \
    file://dvb-usb-rtl2832.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://xc3028-fix-center-frequency.patch \
    file://cinergy_s2_usb_r2.patch \
    file://af9015-output-full-range-SNR.patch \
    file://dvb-as102.patch \
    file://em28xx_fix_terratec_entries.patch \
    file://em28xx_add_terratec_h5_rev3.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://nfs-max-rwsize-8k.patch \
    file://linux-3.1.1-dvb-sky.patch \
    "

SRC_URI_append_vusolo = " file://linux_3.1.1.patch"
SRC_URI_append_vuduo = " file://linux_3.1.1.patch file://linux-sata_brcm.patch"
SRC_URI_append_vuuno = " file://linux_3.1.1.patch file://linux-sata_brcm.patch"
SRC_URI_append_vuultimo = " file://linux-sata_brcm.patch"

S = "${WORKDIR}/linux-${PV}"

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
            flash_erase  /dev/mtd1 0 0
            nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}
