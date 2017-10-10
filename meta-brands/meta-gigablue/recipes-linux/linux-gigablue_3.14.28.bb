SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"

MODULE = "linux-3.14.28"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = "r5"

SRC_URI[md5sum] = "c1e96f702ca737630f5acb0dce2388e7"
SRC_URI[sha256sum] = "72928012a7dbacbf95a371d9faa6800a20afd6b106958298cfc41028878aac4e"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://impex-sat.de/gigablue/linux/gigablue-linux-${PV}-20170331.tar.gz \
	file://defconfig \
	file://linux_dvb_adapter.patch \
	file://bcm_genet_disable_warn.patch \
	file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
	file://usb_core_hub_msleep.patch \
	file://rtl8712_fix_build_error.patch \
	file://kernel-add-support-for-gcc6.patch \
	file://0001-Support-TBS-USB-drivers.patch \
	file://0001-STV-Add-PLS-support.patch \
	file://0001-STV-Add-SNR-Signal-report-parameters.patch \
	file://linux_prevent_usb_dma_from_bmem.patch \
    file://genksyms_fix_typeof_handling.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p3
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm_kernel-image () {
}

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-gigablue-${KV}:"

do_rm_work() {
}
