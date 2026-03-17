DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.35"

COMPATIBLE_MACHINE = "dagsmv200"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = "2"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "4c8f204781ac5a4a83918d5527fbcab0"
SRC_URI[kernel.sha256sum] = "ce63b433241890fc64df4a21c8fa0dea9d10c4f7100e47485cf687727c1f708d"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/dags/linux-4.4.35.tar.xz;name=kernel \
	file://defconfig \
	file://findkerneldevice.sh \
	file://initramfs-subdirboot.cpio.gz;unpack=0 \
	${KERNEL_PATCH_WIFI_COMPAT} \
	file://vmap.patch \
	file://0004_swifthooking.patch \
	${KERNEL_PATCH_FIX_ATTRIBUTES_GCC9} \
	${KERNEL_PATCH_FIX_LOG2} \
	${KERNEL_PATCH_BINUTILS241_V3} \
	${KERNEL_PATCH_WIFI_CFG80211} \
	"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
    ${KERNEL_PATCH_MISC_WG_KCONFIG} \
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
