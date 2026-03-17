inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = ".27"

COMPATIBLE_MACHINE = "^(dm900|dm920)$"

PATCHREV = "6fa88d2001194cbff63ad94cb713b6cd5ea02739"
PATCHLEVEL = "79"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    https://source.mynonpublic.com/dreambox/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
    ${KERNEL_PATCH_TBS_USB_314A} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://genksyms_fix_typeof_handling.patch \
    ${KERNEL_PATCHES_DVB_TDA18273} \
    ${KERNEL_PATCHES_DVB_SI2157_OLD} \
    ${KERNEL_PATCHES_DVB_SI2168_OLD} \
    file://0003-cxusb-Geniatech-T230-support.patch \
    file://CONFIG_DVB_SP2.patch \
    file://dvbsky.patch \
    file://rtl2832u-2.patch \
    file://0006-makefile-silence-packed-not-aligned-warn.patch \
    file://0007-overlayfs.patch \
    ${KERNEL_PATCH_BINUTILS241_V6} \
    ${KERNEL_PATCH_GCC_SERIES} \
    file://chkroot-multiboot.cpio.xz;unpack=0 \
"

SRC_URI[kernel.md5sum] = "b621207b3f6ecbb67db18b13258f8ea8"
SRC_URI[kernel.sha256sum] = "61558aa490855f42b6340d1a1596be47454909629327c49a5e4e10268065dffa"
SRC_URI[stable-patch.md5sum] = "c2bc200bf9eb5a49e2137e039ea27884"
SRC_URI[stable-patch.sha256sum] = "b391b76f3a5c6c8cf7234f8c01821b88584ddf90f45323e09c126c5e7624b12c"
SRC_URI[dream-patch.md5sum] = "b8e267850e54a1d13be41456be5ec4b5"
SRC_URI[dream-patch.sha256sum] = "85a18df9f07e221c0fd305cc213e5557d9006a40b3229bf9d13e5bc9ba8e2371"

require linux-dreambox-3.14.inc

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

CMDLINE = "bmem=640M@384M bmem=384M@2048M console=ttyS0,1000000 root=/dev/mmcblk0p2 rootwait rootfstype=ext4 coherent_pool=2M"

DEFCONFIG = "${MACHINE}"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/chkroot-multiboot.cpio.xz ${B}/
}

BRCM_PATCHLEVEL = "1.17"

LINUX_VERSION = "${PV}-${BRCM_PATCHLEVEL}-${MACHINE}"
KERNEL_IMAGETYPE = "zImage"
KERNEL_DEVICETREE = "dreambox-dm900.dtb"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

do_rm_work() {
}
