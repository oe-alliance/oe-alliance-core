require linux.inc

INC_PR = "r8"

PACKAGES_DYNAMIC = "kernel-*"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

USB_ROOT = "/dev/sdb2"
USB_ROOT_dm8000 = "/dev/sdf2"

do_install_append() {
        gzip -9 ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
        echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
        echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
        echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${USB_CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
}

FILES_kernel-image += "/${KERNEL_IMAGEDEST}/autoexec*.bat"

python __anonymous () {
        import bb, re
        kv = d.getVar('DREAMBOX_KERNEL_VERSION', True)
        pv = d.getVar('PV', True)
        if kv != pv:
                raise bb.parse.SkipPackage("incompatible with kernel version %s" % kv)
}

pkg_preinst_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then mount -o remount,rw /${KERNEL_IMAGEDEST}; fi
}
pkg_prerm_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then mount -o remount,rw /${KERNEL_IMAGEDEST}; fi
}
pkg_postinst_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then mount -o remount,ro /${KERNEL_IMAGEDEST}; fi
}
pkg_postrm_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then mount -o remount,ro /${KERNEL_IMAGEDEST}; fi
}

# Do not use update-alternatives!
pkg_postinst_kernel () {
}
pkg_postrm_kernel () {
}

CMDLINE = "root=/dev/mtdblock3 rootfstype=jffs2 rw ${CMDLINE_CONSOLE}"
USB_CMDLINE = "root=${USB_ROOT} rootdelay=10 rw ${CMDLINE_CONSOLE}"

LOCALVERSION = "-${MACHINE}"
