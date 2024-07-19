SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.10.12"

COMPATIBLE_MACHINE = "^(sh1|h3|h4|h5|h6|h7|h17|lc|i55)$"

inherit kernel machine_kernel_pr

SRC_URI[mips.md5sum] = "3c42df14db9d12041802f4c8fec88e17"
SRC_URI[mips.sha256sum] = "738896d2682211d2079eeaa1c7b8bdd0fe75eb90cd12dff2fc5aeb3cc02562bc"
SRC_URI[arm.md5sum] = "bda1c09ed92a805cedc6770c0dd40e81"
SRC_URI[arm.sha256sum] = "67a3ac98727595a399d5c399d3b66a7fadbe8136ac517e08decba5ea6964674a"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = "17"

SRC_URI += "https://source.mynonpublic.com/zgemma/linux-${PV}-${ARCH}.tar.gz;name=${ARCH} \
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
    file://fix-multiple-defs-yyloc.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

SRC_URI:append:mipsel = " \
    file://0001-add-dmx-source-timecode.patch \
    file://0002-nand-ecc-strength-and-bitflip.patch \
    file://sdio-pinmux.patch \
    file://fix-never-be-null_outside-array-bounds-gcc-12.patch \
    "

SRC_URI:append:arm = " \
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

KERNEL_OUTPUT:mips = "vmlinux"
KERNEL_IMAGETYPE:mips = "vmlinux"

FILES:${KERNEL_PACKAGE_NAME}-image:mips = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_install:append:mips () {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image:mips () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}

# Linux ARM Models

KERNEL_OUTPUT:arm = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_IMAGETYPE:arm = "zImage"
FILES:${KERNEL_PACKAGE_NAME}-image:arm = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure:prepend:arm() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append:arm() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image:arm () {
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

# extra tasks
addtask kernel_link_images after do_compile before do_install