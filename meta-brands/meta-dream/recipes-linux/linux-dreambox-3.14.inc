require linux-3.14.inc

do_configure:prepend() {
        sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}

KERNEL_ENABLE_CGROUPS = "1"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG:kernel = "kernel"
PKG:${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG:${KERNEL_PACKAGE_NAME}-image = "kernel-image"
PKG:${KERNEL_PACKAGE_NAME}-image-zimage = "kernel-image"
RPROVIDES:${KERNEL_PACKAGE_NAME} = "kernel-${LINUX_VERSION}"
RPROVIDES:${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES:${KERNEL_PACKAGE_NAME}-image = "kernel-image-${KERNEL_VERSION} kernel-${KERNEL_IMAGETYPE}"
#RPROVIDES:${KERNEL_PACKAGE_NAME}-image-zimage = "kernel-image"
RDEPENDS:${KERNEL_PACKAGE_NAME}-image = "kernel-devicetree flash-scripts"

pkg_postinst:kernel-image () {
#!/bin/sh
if [ -z "$D" ]; then
    flash-kernel /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${LINUX_VERSION}
fi
}
