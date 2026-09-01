SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.0.1"

COMPATIBLE_MACHINE = "^(7000s|7005s|7100s|7105s|7210s|7215s|7220s|7225s|7300s|7400s)$"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[md5sum] = "53d1614e476bc1141b35266cb31ba091"
SRC_URI[sha256sum] = "385d8efec92b5d3bc8e16c37673e4a2a38a6541b684311650040aa5d67508c3e"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r4"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/ceryon/ceryon-linux-${PV}.tgz \
        file://defconfig \
        file://dvb-usb-i2c_duplicate.patch \
        ${KERNEL_PATCH_GCC_SERIES} \
        ${KERNEL_PATCH_TBS_USB_401} \
        ${KERNEL_PATCH_TBS_USB_ENUM} \
        ${KERNEL_PATCHES_DVB_STV_SERIES} \
        ${KERNEL_PATCH_BRCM_CHIPS} \
        file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
        ${KERNEL_PATCH_FIX_NEVER_NULL} \
        ${KERNEL_PATCH_BINUTILS241_V1} \
        file://block2mtd.patch \
        file://initramfs-mipsel.cpio.xz;unpack=0 \
        ${KERNEL_PATCH_WIFI_EXTAUTH_40} \
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
