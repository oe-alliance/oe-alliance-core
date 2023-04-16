require linux-3.4.inc

RPROVIDES:${KERNEL_PACKAGE_NAME}-image = "kernel-${KERNEL_IMAGETYPE}"

do_install:append() {
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

FILES:${KERNEL_PACKAGE_NAME}-image-dbg = "/${KERNEL_IMAGEDEST}/.debug"
FILES:${KERNEL_PACKAGE_NAME}-vmlinux = "/boot/vmlinux-${LINUX_VERSION}"

python populate_packages:prepend() {
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
pkg_postinst:kernel-image () {
}
pkg_postrm:kernel-image () {
}
