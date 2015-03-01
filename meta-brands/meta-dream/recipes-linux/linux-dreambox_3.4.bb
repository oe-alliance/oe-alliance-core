inherit machine_kernel_pr

PATCHREV = "209710a594b4d5643a3bfbc01f45ede012bc7da7"
PATCHLEVEL = "105"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.4.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.4.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
"
SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "0f43fcca926776fd6fe849dcf62e633e"
SRC_URI[stable-patch.sha256sum] = "e596f2b874a6a8f31a6f93cb40457694d30ff641d760b70a7419c983e67d1317"
SRC_URI[dream-patch.md5sum] = "3eb59164d4c97c22619b78782bc53cbf"
SRC_URI[dream-patch.sha256sum] = "20fa24a54c9d0c40454971585beb0cc27d45c3f48979d12760f1cf47a16420ba"

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

CMDLINE = "bmem=384M@640M memc1=768M console=ttyS0,1000000 root=/dev/mmcblk0p1 rootwait rootfstype=ext4"

KERNEL_VERSION = "3.4-3.0-${MACHINE}"
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