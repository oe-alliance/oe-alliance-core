inherit machine_kernel_pr

MACHINE_KERNEL_PR_append = ".9"

PATCHREV = "f716cadd1904ce001ec3f57e7e129a08b11d7c27"
PATCHLEVEL = "101"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.4.tar.xz;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.4.${PATCHLEVEL}.xz;apply=yes;name=stable-patch \
    http://dreamboxupdate.com/download/kernel-patches/linux-dreambox-${PV}-${PATCHREV}.patch.xz;apply=yes;name=dream-patch \
    file://defconfig \
"
SRC_URI[kernel.md5sum] = "967f72983655e2479f951195953e8480"
SRC_URI[kernel.sha256sum] = "ff3dee6a855873d12487a6f4070ec2f7996d073019171361c955639664baa0c6"
SRC_URI[stable-patch.md5sum] = "bc5a50cfaea51528e0f78b5d19684a8d"
SRC_URI[stable-patch.sha256sum] = "4f208c4ac5d76580680d9fa8fd35d5d35c7699565a59280d5173d66538c0a219"
SRC_URI[dream-patch.md5sum] = "1b866bf59efd85411ccaf57aa99b463c"
SRC_URI[dream-patch.sha256sum] = "e9fffc9165b14b21bd8db994bdc6378ddf736832c79f5504db9a24f896665490"

S = "${WORKDIR}/linux-3.4"

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