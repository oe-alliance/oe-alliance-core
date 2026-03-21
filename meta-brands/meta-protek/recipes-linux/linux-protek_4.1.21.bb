SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel machine_kernel_pr kernel-fixups

COMPATIBLE_MACHINE = "9900lx"

KERNEL_RELEASE = "4.1.21"
SRCDATE = "20160407"

SRC_URI[md5sum] = "e7ba35d427bfa40d78cd6e23db7872a2"
SRC_URI[sha256sum] = "88f648e462e9d37c6ed9401b33ee1dd08495e9f66b9c653aefd9fd0a4f5afb26"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r3"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/protek/protek-linux-${PV}-${SRCDATE}.tar.xz \
    file://defconfig \
    file://0001-regmap-add-regmap_write_bits.patch \
    file://0002-af9035-fix-device-order-in-ID-list.patch \
    file://0003-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-soloHD.patch \
    ${KERNEL_PATCHES_DVB_USB_SERIES} \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://fix-proc-cputype.patch \
    file://iosched-slice_idle-1.patch \
    file://tda18271-advertise-supported-delsys.patch \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_TBS_USB_41} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0004-makefile-disable-warnings.patch \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    ${KERNEL_PATCH_BINUTILS241_V1} \
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

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

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
