inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".13"

PATCHREV = "e7fe570494f9341822e3f184b1bd3364ee4e0a50"
PATCHLEVEL = "113"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-${PV}.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-${PV}.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://0001-BRCMSTB-dont-enable-new-i2c-driver-by-default.-it-co.patch \
    file://dvb_frontend-Multistream-support-3.4.patch \
    file://0001-dream-dont-reset-wktmr-on-warm-boot.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://0001-block2mtd-add-possibility-to-change-the-writesize.patch \
    file://0002-block2mtd-add-possibility-to-remove-block2mtd-device.patch \
    file://0003-mtd-block2mtd-throttle-writes-by-calling-balance_dir.patch \
    file://0004-enabled-block2mtd-driver-for-dm520-build-mtdram-kern.patch \
    file://defconfig \
"
SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "cbd978b714f37b648fbcf92482372223"
SRC_URI[stable-patch.sha256sum] = "d5492eeaadcf12aaad471011066e447907999035c26368da8e4f82b1871ef03a"
SRC_URI[dream-patch.md5sum] = "7c8958c638a9cf150ee6e0693518a902"
SRC_URI[dream-patch.sha256sum] = "7124e4145b5edb8179c679f842cd4349e1d0e7635c369385f246170cad7d6870"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

do_configure_prepend() {
        sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}

require linux-dreambox2.inc
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

RDEPENDS_kernel-image = "flash-scripts"

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