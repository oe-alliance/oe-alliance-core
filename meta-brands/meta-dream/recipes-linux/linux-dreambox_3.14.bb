inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".24"

PATCHREV = "6fa88d2001194cbff63ad94cb713b6cd5ea02739"
PATCHLEVEL = "79"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
    file://0001-Support-TBS-USB-drivers.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://blindscan2.patch \
"

SRC_URI[kernel.md5sum] = "b621207b3f6ecbb67db18b13258f8ea8"
SRC_URI[kernel.sha256sum] = "61558aa490855f42b6340d1a1596be47454909629327c49a5e4e10268065dffa"
SRC_URI[stable-patch.md5sum] = "c2bc200bf9eb5a49e2137e039ea27884"
SRC_URI[stable-patch.sha256sum] = "b391b76f3a5c6c8cf7234f8c01821b88584ddf90f45323e09c126c5e7624b12c"
SRC_URI[dream-patch.md5sum] = "b8e267850e54a1d13be41456be5ec4b5"
SRC_URI[dream-patch.sha256sum] = "85a18df9f07e221c0fd305cc213e5557d9006a40b3229bf9d13e5bc9ba8e2371"

require linux-dreambox4.inc
require recipes-kernel/linux/linux-dtb.inc

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

CMDLINE = "bmem=640M@384M bmem=384M@2048M console=ttyS0,1000000 root=/dev/mmcblk0p2 rootwait rootfstype=ext4 coherent_pool=2M"

DEFCONFIG = "${MACHINE}"

BRCM_PATCHLEVEL = "1.17"

LINUX_VERSION = "${PV}-${BRCM_PATCHLEVEL}-${MACHINE}"
KERNEL_IMAGETYPE = "zImage"
KERNEL_DEVICETREE = "dreambox-dm900.dtb"

do_rm_work() {
}
