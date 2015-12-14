inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".1"

PATCHREV = "a904cb737a1d95034adc4717200cdf1e52ec8549"
PATCHLEVEL = "110"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.4.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.4.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
    file://0001-xhci-Return-correct-number-of-tranferred-bytes-for-s.patch \
    file://0002-xhci-fix-off-by-one-error-in-TRB-DMA-address-boundar.patch \
"
SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "4225d2f3a2bdd2d2fed94b5b83a5a8bb"
SRC_URI[stable-patch.sha256sum] = "132362637b7ba272d58acbedb172a7233c238ec1d794af111af55ee8a406e12d"
SRC_URI[dream-patch.md5sum] = "81d8689bc16214cd1737e029d39bb46c"
SRC_URI[dream-patch.sha256sum] = "4e4d40bc4ea6d1425348c6a01aad637686da835378ef78896e89686c38884d44"

S = "${WORKDIR}/linux-3.4"
B = "${WORKDIR}/build"

do_configure_prepend() {
        sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}
do_compile_append() {
        gzip < vmlinux > vmlinuz
}

require linux-dreambox2.inc
require linux-extra-image.inc

CMDLINE = "bmem=512M@512M memc1=768M console=ttyS0,1000000 root=/dev/mmcblk0p1 rootwait rootfstype=ext4"

KERNEL_VERSION = "3.4-3.5-${MACHINE}"
KERNEL_IMAGETYPE = "vmlinux.bin"
KERNEL_ALT_IMAGETYPE = "vmlinux"
KERNEL_EXTRA_IMAGETYPE = "vmlinuz"
KERNEL_EXTRA_OUTPUT = "vmlinuz"
KERNEL_ENABLE_CGROUPS = "1"

RDEPENDS_kernel-image = "flash-scripts"

pkg_postinst_kernel-image () {
if [ -z "$D" ]; then
    flash-kernel /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
fi
}

COMPATIBLE_MACHINE = "^(dm820|dm7080)$"

do_rm_work() {
}