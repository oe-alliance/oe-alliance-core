SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.10.12"

COMPATIBLE_MACHINE = "^(sh1|h3|h4|h5|h6|h7|h17|lc|i55)$"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[mips.md5sum] = "3c42df14db9d12041802f4c8fec88e17"
SRC_URI[mips.sha256sum] = "738896d2682211d2079eeaa1c7b8bdd0fe75eb90cd12dff2fc5aeb3cc02562bc"
SRC_URI[arm.md5sum] = "bda1c09ed92a805cedc6770c0dd40e81"
SRC_URI[arm.sha256sum] = "67a3ac98727595a399d5c399d3b66a7fadbe8136ac517e08decba5ea6964674a"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r20"

SRC_URI += "https://source.mynonpublic.com/zgemma/linux-${PV}-${ARCH}.tar.gz;name=${ARCH} \
    file://defconfig \
    ${KERNEL_PATCHES_SERIES_4_10} \
    ${KERNEL_PATCHES_DVB_SI2157} \
    ${KERNEL_PATCHES_DVB_SI2168} \
    ${KERNEL_PATCHES_DVB_MYGICA_V333C3} \
    ${KERNEL_PATCHES_DVB_MYGICA_V334} \
    ${KERNEL_PATCHES_DVB_MYGICA_V335} \
    file://0005-xbox-one-tuner-4.10.patch \
    file://Hauppauge-dualHD.patch \
    file://quirks.patch \
    file://dib0700.patch \
    "

SRC_URI:append:mipsel = " \
    ${KERNEL_PATCH_DVB_DMX_TIMECODE} \
    ${KERNEL_PATCH_BRCM_NAND_ECC} \
    ${KERNEL_PATCH_BRCM_SDIO_PINMUX} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

SRC_URI:append:arm = " \
    file://export_pmpoweroffprepare.patch \
    file://findkerneldevice.sh \
    ${KERNEL_PATCH_DVB_RESERVE} \
    file://blacklist_mmc0.patch \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

RPROVIDES:${KERNEL_PACKAGE_NAME}-image:mipsel += "kernel-${KERNEL_IMAGETYPE}"

# Linux MIPS Models

KERNEL_OUTPUT:mipsel = "vmlinux"
KERNEL_IMAGETYPE:mipsel = "vmlinux"

FILES:${KERNEL_PACKAGE_NAME}-image:mipsel = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_configure:prepend:mipsel() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append:mipsel () {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image:mipsel () {
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
