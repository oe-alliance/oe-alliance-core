require linux4.inc

do_configure_prepend() {
        sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}

KERNEL_ENABLE_CGROUPS = "1"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel = "kernel"
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
PKG_kernel-image-zimage = "kernel-image"
RPROVIDES_kernel = "kernel-${LINUX_VERSION}"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"
RPROVIDES_kernel-image-zimage = "kernel-image"
RDEPENDS_kernel-image = "kernel-devicetree flash-scripts"


pkg_postinst_kernel-image () {
#!/bin/sh
if [ -z "$D" ]; then
    flash-kernel /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${LINUX_VERSION}
fi
}

