DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "v6.18.41"

COMPATIBLE_MACHINE = "^sf8008$"

inherit kernel machine_kernel_pr

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI += "https://www.kernel.org/pub/linux/kernel/v6.x/linux-${PV}.tar.gz \
    file://defconfig \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://0001-dt-bindings-clock-hisi-crg-add-Hi3798MV200-compatibl.patch \
    file://0002-clk-hisilicon-add-CRG-driver-for-Hi3798MV200.patch \
    file://0003-net-hix5hd2_gmac-accept-the-RGMII-internal-delay-mod.patch \
    file://0004-dt-bindings-net-hisilicon-hix5hd2-gmac-add-Hi3798MV2.patch \
    file://0005-dt-bindings-vendor-prefixes-add-Octagon.patch \
    file://0006-dt-bindings-arm-hisilicon-add-Hi3798MV200-based-boar.patch \
    file://0007-arm64-dts-hisilicon-add-Hi3798MV200-SoC-dtsi.patch \
    file://0008-arm64-dts-hisilicon-add-Octagon-SF8008-board.patch \
    file://0009-arm64-defconfig-enable-Hi3798MV200-peripherals.patch \
    file://0010-ARM-hisi-add-Hi3798MV200-machine-support.patch \
    file://0011-ARM-hisi-add-Hi3798MV200-SMP-bring-up.patch \
    file://0012-ARM-dts-hisilicon-add-Hi3798MV200-SoC-dtsi.patch \
    file://0013-ARM-dts-hisilicon-add-Octagon-SF8008-board.patch \
    file://0014-ARM-multi_v7_defconfig-enable-Hi3798MV200.patch \
    file://0015-ARM-hisi_defconfig-enable-Hi3798MV200.patch \
    file://0016-clk-hisilicon-fix-error-handling-in-hisi_reset_init.patch \
    file://0017-net-hix5hd2_gmac-hold-the-MAC-core-reset-long-enough.patch \
    file://0018-ARM-hisi-join-Hi3798MV200-secondaries-to-the-coheren.patch \
    file://0019-dt-bindings-phy-hisilicon-inno-usb2-phy-add-Hi3798MV.patch \
    file://0020-phy-hisi-inno-usb2-add-Hi3798MV200-support.patch \
    file://0021-phy-hisi-inno-usb2-wait-long-enough-for-the-UTMI-res.patch \
    file://0022-ARM-dts-hisilicon-describe-the-SF8008-bootloader-fra.patch \
    file://0023-net-hix5hd2_gmac-support-the-four-word-descriptor-la.patch \
    file://0024-net-hix5hd2_gmac-do-not-claim-TSO-on-the-Hi3798MV200.patch \
    file://0025-media-rc-add-keymap-for-the-Octagon-SF8008-remote.patch \
    file://0026-media-rc-ir-hix5hd2-report-a-real-duration-for-the-t.patch \
    file://0027-ARM-dts-hi3798mv200-add-a-syscon-reboot-node.patch \
    file://0028-drm-hisilicon-add-a-driver-for-the-STB-display-proce.patch \
    file://0029-ARM-dts-hi3798mv200-describe-the-video-display-proce.patch \
"
SRC_URI[sha256sum] = "ec7efb5ce8406adfcf681cae40cfad58c2f91eea97736c2ef66124e9d2cf3cb7"

#SRC_URI:append:sf8008 = " \
#	${KERNEL_PATCH_BRCM_EMMC_REBOOT} \
#"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

# The stock HiSilicon "Fastboot" relocates itself to ~0x00d84000 and refuses a
# kernel whose destination would run into it ("kernel image will overwrite
# uboot!"). The vendor 4.4 kernel set LOADADDR := 0x02000000 in
# arch/arm/mach-hi3798mv2x/Makefile.boot; 6.18 is multiplatform
# (CONFIG_AUTO_ZRELADDR) so there is no Makefile.boot and the value has to be
# supplied here. 0x02000000 sits above the bootloader, so the check passes.
# The zImage decompressor still relocates the kernel down to 0x00008000 itself.
KERNEL_EXTRA_ARGS = "LOADADDR=0x02000000"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} /${KERNEL_IMAGEDEST}/findkerneldevice.sh"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
UBOOT_LOADADDRESS = "0x02000000"
KERNEL_DEVICETREE = "hisilicon/hi3798mv200-octagon-sf8008.dtb"

# The stock HiSilicon bootloader predates device trees: it passes ATAGs and has
# no way to hand a DTB to the kernel, so a plain uImage boots into a silent
# hang. The defconfig asks for CONFIG_BUILD_ARM_APPENDED_DTB_IMAGE, but that is
# a vendor-only symbol that does not exist in mainline, so nothing appends the
# DTB and the option is silently ignored.
#
# Do it here instead: concatenate the DTB onto the zImage and re-wrap the
# result as a uImage. CONFIG_ARM_APPENDED_DTB makes the decompressor look for a
# DTB right after its own end, and CONFIG_ARM_ATAG_DTB_COMPAT merges the
# bootloader's ATAG memory size and command line into that DTB, so the box
# keeps booting with the cmdline from the bootargs partition.
#
# This has to run after kernel-devicetree.bbclass' own do_compile:append, which
# is what builds the .dtb; recipe-level appends are applied after class-level
# ones, so ordering is guaranteed.
do_compile:append() {
	dtb_name=$(basename ${KERNEL_DEVICETREE})
	dtb_path=$(find ${B}/arch/${ARCH}/boot/dts -name "$dtb_name" | head -n1)
	if [ -z "$dtb_path" ]; then
		bbfatal "appended-DTB: $dtb_name not found under ${B}/arch/${ARCH}/boot/dts"
	fi
	if [ ! -e ${B}/arch/${ARCH}/boot/zImage ]; then
		bbfatal "appended-DTB: zImage missing, cannot append $dtb_name"
	fi

	cat ${B}/arch/${ARCH}/boot/zImage "$dtb_path" > ${B}/arch/${ARCH}/boot/zImage-dtb

	uboot-mkimage -A arm -O linux -T kernel -C none \
		-a ${UBOOT_LOADADDRESS} -e ${UBOOT_LOADADDRESS} \
		-n "Linux-${PV}-${MACHINE}" \
		-d ${B}/arch/${ARCH}/boot/zImage-dtb \
		${B}/arch/${ARCH}/boot/uImage

	bbnote "appended-DTB: uImage rebuilt from zImage + $(basename $dtb_path)"
}

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}
#
#kernel_do_install:append() {
#	install -d ${D}/${KERNEL_IMAGEDEST}
#	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
#	install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
#}
#
#pkg_postinst:kernel-image () {
#    if [ "x$D" == "x" ]; then
#        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
#            /${KERNEL_IMAGEDEST}/./findkerneldevice.sh
#            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
#        fi
#    fi
#    true
#}

do_rm_work() {
}
