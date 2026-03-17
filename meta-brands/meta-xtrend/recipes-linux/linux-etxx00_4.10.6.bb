SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.10.6"

COMPATIBLE_MACHINE = "^(et4x00|et8000|et10000)$"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[md5sum] = "e5d32dd03b742e6101fde917dcba837d"
SRC_URI[sha256sum] = "2997b825996beabc25d2428d37d680f56e4fa971500eabd2033a6fc13cf5765e"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r6"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/xtrend/linux-${PV}-${ARCH}.tar.gz \
    file://defconfig \
    file://0001-genet1-1000mbit.patch \
    ${KERNEL_PATCH_BRCM_GENET_PHYADDR} \
    ${KERNEL_PATCH_TBS_USB_CNR} \
    ${KERNEL_PATCH_TBS_USB_46} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    ${KERNEL_PATCH_MISC_REVERT_XHCI} \
    ${KERNEL_PATCHES_DVB_SI2157} \
    ${KERNEL_PATCHES_DVB_SI2168} \
    ${KERNEL_PATCHES_DVB_MYGICA_V333} \
    ${KERNEL_PATCHES_DVB_MYGICA_V334} \
    ${KERNEL_PATCHES_DVB_MYGICA_V335} \
    ${KERNEL_PATCH_WIFI_RTL8XXXU} \
    file://0005-xbox-one-tuner-4.10.patch \
    ${KERNEL_PATCHES_DVB_TDA18250} \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    ${KERNEL_PATCH_BINUTILS241_V5} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append() {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image () {
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
