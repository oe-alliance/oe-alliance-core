require linux-dreambox_${PV}.bb

PROVIDES = ""

PACKAGES = ""

INITRAMFS_IMAGE = "dreambox-rescue-image"

KERNEL_EXTRA_ARGS += "CONFIG_INITRAMFS_SOURCE=${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.cpio"

KERNEL_IMAGE_BASE_NAME = "${KERNEL_IMAGETYPE}-rescue-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
KERNEL_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}-rescue-${MACHINE}"

KERNEL_EXTRA_IMAGE_BASE_NAME = "${KERNEL_EXTRA_IMAGETYPE}-rescue-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
KERNEL_EXTRA_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
KERNEL_EXTRA_IMAGE_SYMLINK_NAME = "${KERNEL_EXTRA_IMAGETYPE}-rescue-${MACHINE}"

MODULE_TARBALL_DEPLOY = "0"

do_compile[depends] += "${INITRAMFS_IMAGE}:do_rootfs"

# disable unneeded tasks
do_compile_kernelmodules[noexec] = "1"
do_install[noexec] = "1"
do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_deb[noexec] = "1"
do_package_ipk[noexec] = "1"
do_package_rpm[noexec] = "1"
do_package_tar[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_rpm[noexec] = "1"
do_package_write_tar[noexec] = "1"
do_populate_sysroot[noexec] = "1"
