SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

KERNEL_RELEASE = "4.10.0"

inherit kernel machine_kernel_pr

SRC_URI[mips.md5sum] = "1bca7dc4f68196efe7cf8af085841851"
SRC_URI[mips.sha256sum] = "0b53d7cf932da13e4dc81856c4041e409b4c44fbc533ab5c99dcf22ff2b79a63"
SRC_URI[arm.md5sum] = "3826019f1a8d60b12937e27192501af2"
SRC_URI[arm.sha256sum] = "bf4d8196d185d1b4973f5c9053f506e65883869b92d05940fe7091574a8ecdf7"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".1"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://source.mynonpublic.com/zgemma/linux-${PV}-${ARCH}.tar.gz;name=${ARCH} \
    file://defconfig \
    file://TBS-fixes-for-4.10-kernel.patch \
    file://0001-Support-TBS-USB-drivers-for-4.6-kernel.patch \
    file://0001-TBS-fixes-for-4.6-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    "

SRC_URI_append_mipsel = " \
    file://0001-add-dmx-source-timecode.patch \
    file://0002-nand-ecc-strength-and-bitflip.patch \
    file://sdio-pinmux.patch \
    "

SRC_URI_append_arm = " \
    file://findkerneldevice.py \
    file://reserve_dvb_adapter_0.patch \
    file://blacklist_mmc0.patch \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"

# Linux MIPS Models

KERNEL_OUTPUT_mips = "vmlinux"
KERNEL_IMAGETYPE_mips = "vmlinux"
KERNEL_IMAGEDEST_mips = "/boot"

FILES_kernel-image_mips = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"


kernel_do_install_append_mips() {
	${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image_mips () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/mtd1 0 0
			nandwrite -p /dev/mtd1 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}

# Linux ARM Models

KERNEL_OUTPUT_arm = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_IMAGETYPE_arm = "zImage"
KERNEL_IMAGEDEST_arm = "tmp"

FILES_kernel-image_arm = "/${KERNEL_IMAGEDEST}/zImage /${KERNEL_IMAGEDEST}/findkerneldevice.py"

kernel_do_install_append_arm() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${WORKDIR}/findkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst_kernel-image_arm () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            python /${KERNEL_IMAGEDEST}/findkerneldevice.py
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install