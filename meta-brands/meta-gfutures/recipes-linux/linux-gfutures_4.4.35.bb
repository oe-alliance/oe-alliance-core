DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
VER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

KERNEL_RELEASE = "4.4.35"

SRCDATE = "20200219"

COMPATIBLE_MACHINE = "^(hd60|hd61|hd66se)$"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "19"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "f9e67e2d0ceab518510413f8f4315bc3"
SRC_URI[kernel.ha256sum] = "45ae717b966a74326fd7297d81b3a17fd5b3962b7704170682a615ca7cdec644"

SRC_URI = "https://source.mynonpublic.com/gfutures/linux-${PV}-${SRCDATE}-${ARCH}.tar.gz;name=kernel \
	file://defconfig \
	file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
	file://0003-dont-mark-register-as-const.patch \
	file://0001-remote.patch \
	file://initramfs-subdirboot.cpio.gz;unpack=0 \
	file://HauppaugeWinTV-dualHD.patch \
	file://dib7000-linux_4.4.179.patch \
	file://dvb-usb-linux_4.4.179.patch \
	file://wifi-linux_4.4.183.patch \
	file://findkerneldevice.sh \
	file://move-default-dialect-to-SMB3.patch \
	file://0004-linux-fix-buffer-size-warning-error.patch \
	file://modules_mark__inittest__exittest_as__maybe_unused.patch \
	file://includelinuxmodule_h_copy__init__exit_attrs_to_initcleanup_module.patch \
	file://Backport_minimal_compiler_attributes_h_to_support_GCC_9.patch \
	file://0005-xbox-one-tuner-4.4.patch \
	file://0006-dvb-media-tda18250-support-for-new-silicon-tuner.patch \
	file://0007-dvb-mn88472-staging.patch \
	file://mn88472_reset_stream_ID_reg_if_no_PLP_given.patch \
	file://fix-multiple-defs-yyloc.patch \
	file://fix-build-with-binutils-2.41.patch \
	file://cfg80211_Add_option_to_report_the_bss_entry_in_connect_result.patch \
	"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
                    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
                    file://wg-kconfig.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image:hd41 = " "
FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure:prepend() {
    install -d ${B}/usr
    install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append:hd41() {
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image:hd41() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
		fi
	fi
	true
}

pkg_postinst:kernel-image() {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
			/${KERNEL_IMAGEDEST}/./findkerneldevice.sh
			dd if=${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
		fi
	fi
	true
}

do_rm_work() {
}
