require linux-4.9.inc

do_configure:prepend() {
        sed -e "/^SUBLEVEL = /d" -i ${S}/Makefile
}

KERNEL_ENABLE_CGROUPS = "1"

RDEPENDS:${KERNEL_PACKAGE_NAME}-image = "kernel-devicetree flash-scripts"

KERNEL_FLASH_ARGS ?= ""

pkg_postinst:${KERNEL_PACKAGE_NAME}-image () {
#!/bin/sh
[ -n "$D" ] || flash-kernel ${KERNEL_FLASH_ARGS} /boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

INITRAMFS_BASE_NAME = "rescue-${PV}-${PR}-${MACHINE}-${DATETIME}"

def kernel_console(d):
    console = ','.join(reversed(next(iter((d.getVar('SERIAL_CONSOLES') or '').split()), 'null').split(';')))
    return 'console=%s' % console
