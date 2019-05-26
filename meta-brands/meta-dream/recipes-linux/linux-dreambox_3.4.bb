inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".14"

PATCHREV = "30070c78a23d461935d9db0b6ce03afc70a10c51"
PATCHLEVEL = "113"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://dvb_frontend-Multistream-support-3.4.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://kernel-add-support-for-gcc9.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://defconfig \
    file://0001-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0002-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0003-makefile-silence-packed-not-aligned-warn.patch \
    file://0004-fcrypt-fix-bitoperation-for-gcc.patch \
"

SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "cbd978b714f37b648fbcf92482372223"
SRC_URI[stable-patch.sha256sum] = "d5492eeaadcf12aaad471011066e447907999035c26368da8e4f82b1871ef03a"
SRC_URI[dream-patch.md5sum] = "75844e4a206fd6ec3aeeaf1380c60b99"
SRC_URI[dream-patch.sha256sum] = "5ed3938ec088a868bcd344fd03adedbcefc5198c5255bd48f26fb87e1f8b7b07"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

do_configure_prepend() {
    rm -rf ${STAGING_KERNEL_DIR}/.config
    rm -rf ${STAGING_KERNEL_DIR}/.config.old
    sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}

require linux-dreambox-3.4.inc
require linux-extra-image.inc

CMDLINE = "${@bb.utils.contains('MACHINE', 'dm520', \
    'bmem=192M@64M console=ttyS0,1000000 ubi.mtd=rootfs root=ubi0:dreambox-rootfs rootfstype=ubifs rw', \
    'bmem=512M@512M memc1=768M console=ttyS0,1000000 root=/dev/mmcblk0p1 rootwait rootfstype=ext4', d)} \
"

BRCM_PATCHLEVEL = "4.0"

LINUX_VERSION = "${PV}-${BRCM_PATCHLEVEL}-${MACHINE}"
KERNEL_IMAGETYPE = "${@bb.utils.contains('MACHINE', 'dm520', 'vmlinux.gz', 'vmlinux.bin', d)}"
KERNEL_IMAGETYPES = "${@bb.utils.contains('MACHINE', 'dm520', '', 'vmlinux.gz', d)}"

KERNEL_ENABLE_CGROUPS = "1"

RDEPENDS_${KERNEL_PACKAGE_NAME}-image = "flash-scripts"

pkg_postinst_kernel-image () {
#!/bin/sh
if [ -z "$D" ]; then
    flash-kernel /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${LINUX_VERSION}
fi
}

COMPATIBLE_MACHINE = "^(dm520|dm820|dm7080)$"

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
