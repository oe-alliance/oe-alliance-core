DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.35"
SRCDATE = "20181224"

COMPATIBLE_MACHINE = "^(sf8008|sf8008m)$"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = "35"

SRC_URI[md5sum] = "ad7eab17a5071a0d5f9ff44eb44e027d"
SRC_URI[sha256sum] = "0654d5aa21c51eaea46f7203014afe60052ec0990a92b9e289e1ca8a2793907c"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/octagon/octagon-linux-${PV}-${SRCDATE}.tar.gz \
    file://defconfig \
    ${KERNEL_PATCHES_SERIES_4_4_35} \
    ${KERNEL_PATCH_DVB_CORE_FIX} \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://findkerneldevice.sh \
    file://extend_modules_space.patch \
    file://JMS583.patch \
    ${KERNEL_PATCH_WIFI_EXTAUTH_44} \
"

SRC_URI:append:sf8008m = " \
	file://fix-index-for-usb.patch \
"

SRC_URI:append:sf8008 = " \
	${KERNEL_PATCH_BRCM_EMMC_REBOOT} \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} /${KERNEL_IMAGEDEST}/findkerneldevice.sh"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image () {
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
