require linux-3.4.inc

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_${KERNEL_PACKAGE_NAME}-base = "kernel-${LINUX_VERSION}"
RPROVIDES_${KERNEL_PACKAGE_NAME}-image = "kernel-image-${LINUX_VERSION}"

do_install_append() {
        if [ "${KERNEL_IMAGETYPE}" = "vmlinux" ]; then
                # We want a stripped kernel that gets gzipped and booted from and
                # an unstripped version for debugging, which will get into the
                # kernel-vmlinux package. When booting vmlinux directly, work on a copy.
                if [ "${KERNEL_IMAGEDEST}" = "boot" ]; then
                        install -m644 ${D}/boot/vmlinux-${LINUX_VERSION} ${D}/boot/vmlinux.nostrip
                fi
                # Mark the kernel as executable, so it gets stripped during do_package.
                chmod +x ${D}/${KERNEL_IMAGEDEST}/vmlinux-${LINUX_VERSION}
        fi
        install -d ${D}/usr/src/kernel
}

PACKAGES += "kernel-image-dbg"

FILES_${KERNEL_PACKAGE_NAME}-image-dbg = "/${KERNEL_IMAGEDEST}/.debug"
FILES_${KERNEL_PACKAGE_NAME}-vmlinux = "/boot/vmlinux-${LINUX_VERSION}"

python populate_packages_prepend() {
    import shutil
    imagetype = d.getVar('KERNEL_IMAGETYPE', True)
    if imagetype == "vmlinux":
        pkgd = d.getVar('PKGD', True)
        imagedest = d.getVar('KERNEL_IMAGEDEST', True)
        version = d.getVar('LINUX_VERSION', True)
        os.system("gzip -9 %s/%s/vmlinux-%s" % (pkgd, imagedest, version))
        if imagedest == "boot":
            shutil.move("%s/boot/vmlinux.nostrip" % pkgd, "%s/boot/vmlinux-%s" % (pkgd, version))
}

# Do not use update-alternatives!
pkg_postinst_kernel-image () {
}
pkg_postrm_kernel-image () {
}
