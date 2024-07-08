DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.35"

COMPATIBLE_MACHINE = "dagsmv200"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "2"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "4c8f204781ac5a4a83918d5527fbcab0"
SRC_URI[kernel.sha256sum] = "ce63b433241890fc64df4a21c8fa0dea9d10c4f7100e47485cf687727c1f708d"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://en3homeftp.net/pub/down/linux-4.4.35.tar.xz;name=kernel \
	file://defconfig \
	file://findkerneldevice.sh \
	file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
	file://initramfs-subdirboot.cpio.gz;unpack=0 \
	file://wifi-linux_4.4.183.patch \
	file://0003-dont-mark-register-as-const.patch \
	file://vmap.patch \
	file://move-default-dialect-to-SMB3.patch \
	file://0004_swifthooking.patch \
	file://fix-multiple-defs-yyloc.patch \
	file://Backport_minimal_compiler_attributes_h_to_support_GCC_9.patch \
	file://fix-build-with-binutils-2.41.patch \
	file://cfg80211_Add_option_to_report_the_bss_entry_in_connect_result.patch \
	"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
    file://wg-kconfig.patch \
"

S = "${WORKDIR}/sources-unpack/linux-${PV}"
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
