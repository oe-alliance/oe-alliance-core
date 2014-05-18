SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"
KV = "3.1.1"

inherit machine_kernel_pr

SRCDATE = "20120526"
MACHINE_KERNEL_PR_append = ".2"

SRC_URI[md5sum] = "5899790c27f6f4069fbceb64b67635eb"
SRC_URI[sha256sum] = "9a99171a05781c545734c72ea08d3fe3bcb176070b43b7db63ef56a406f7925d"


LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.openmips.com/linux-${KV}-gb800xx-${KV}_${SRCDATE}.tar.gz \
        file://defconfig \
        file://nor-maps-gb800solo.patch \
        file://iosched-slice_idle-1.patch \
        file://fix-eof-dmxdev.patch \
        file://mips-refactor-clearpage-and-copypage_311.patch \
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
