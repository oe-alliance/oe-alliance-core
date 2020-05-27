SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.10.12"

inherit kernel machine_kernel_pr

SRC_URI[mips.md5sum] = "3c42df14db9d12041802f4c8fec88e17"
SRC_URI[mips.sha256sum] = "738896d2682211d2079eeaa1c7b8bdd0fe75eb90cd12dff2fc5aeb3cc02562bc"
SRC_URI[arm.md5sum] = "bda1c09ed92a805cedc6770c0dd40e81"
SRC_URI[arm.sha256sum] = "67a3ac98727595a399d5c399d3b66a7fadbe8136ac517e08decba5ea6964674a"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = "16"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_${KERNEL_PACKAGE_NAME}-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/zgemma/linux-${PV}-${ARCH}.tar.gz;name=${ARCH} \
    file://defconfig \
    file://TBS-fixes-for-4.10-kernel.patch \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://v3-1-3-media-si2157-Add-support-for-Si2141-A10.patch \
    file://v3-2-3-media-si2168-add-support-for-Si2168-D60.patch \
    file://v3-3-3-media-dvbsky-MyGica-T230C-support.patch \
    file://v3-3-4-media-dvbsky-MyGica-T230C-support.patch \
    file://v3-3-5-media-dvbsky-MyGica-T230C-support.patch \
    file://add-more-devices-rtl8xxxu.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://0005-xbox-one-tuner-4.10.patch \
    file://0006-dvb-media-tda18250-support-for-new-silicon-tuner.patch \
    "

SRC_URI_append_mipsel = " \
    file://0001-add-dmx-source-timecode.patch \
    file://0002-nand-ecc-strength-and-bitflip.patch \
    file://sdio-pinmux.patch \
    "

SRC_URI_append_arm = " \
    file://export_pmpoweroffprepare.patch \
    file://findkerneldevice.sh \
    file://reserve_dvb_adapter_0.patch \
    file://blacklist_mmc0.patch \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

# Linux MIPS Models

KERNEL_OUTPUT_mips = "vmlinux.gz"
KERNEL_OUTPUT_DIR_mips = "."
KERNEL_IMAGETYPE_mips = "vmlinux.gz"

pkg_postinst_kernel-image_mips () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

# Linux ARM Models

KERNEL_OUTPUT_arm = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_IMAGETYPE_arm = "zImage"
FILES_${KERNEL_PACKAGE_NAME}-image_arm = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure_prepend_arm() {
	install -d ${B}/usr
	install -m 0644 ${WORKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install_append_arm() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${WORKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst_kernel-image_arm () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            /${KERNEL_IMAGEDEST}/./findkerneldevice.sh
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    true
}

do_rm_work() {
}
