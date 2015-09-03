inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".1"

PATCHREV = "38c24319e8b82eeab5d102eba355718ae8f50c48"
PATCHLEVEL = "106"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.4.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.4.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
    file://backport_bcmgenet_fix_from_3.3-3.6.patch \
    file://0001-re-enable-native-ext2-ext3-support-because-we-have-s.patch \
"
SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "fee956dfe6e1bb4bd0224ceb16338d9b"
SRC_URI[stable-patch.sha256sum] = "eeeeb599392391a8117b8c7d6fdc85153038d1d43e4b9582620b4c62e061e21d"
SRC_URI[dream-patch.md5sum] = "bdca5598db039833c4badf61db3a303f"
SRC_URI[dream-patch.sha256sum] = "1eae07fc11f027d6363abe0d88d0ef7ba5def5e548fc1ddb58d4908af9702a03"

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