require linux.inc

INC_PR = "18"

PACKAGES_DYNAMIC = "kernel-*"

# For packages that RDEPEND on particular kernel modules, list the ones built into
# the kernel here, so that it is known that the kernel has them built in.
KERNEL_BUILTIN_MODULES = ""

KERNEL_BUILTIN_MODULES_dm8000 = "\
    kernel-module-aes-generic \
    kernel-module-crc32c \
    kernel-module-mac80211 \
    kernel-module-cfg80211 \
    kernel-module-ath \
    kernel-module-ath5k \
    kernel-module-sr-mod \
    kernel-module-isofs \
    kernel-module-udf \
    "

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION} ${KERNEL_BUILTIN_MODULES}"

USB_ROOT = "/dev/sdb2"
USB_ROOT_dm8000 = "/dev/sdf2"

do_install_append() {
        ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        gzip -9 ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
        echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
        echo "/boot/bootlogo-${MACHINE}.elf.gz filename=/boot/bootlogo-${MACHINE}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
        echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${USB_CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec_${MACHINE}.bat
}

# The vmlinux package gets priority over the kernel-image, so make it empty so that
# kernel-image gets those files.
FILES_kernel-vmlinux = ""
FILES_kernel-image += "\
    /${KERNEL_IMAGEDEST}/autoexec*.bat \
    "

pkg_preinst_kernel-image() {
    if [ -z "$D" ]
    then
        if mountpoint -q /${KERNEL_IMAGEDEST}
        then
            mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
        else
            mount -t jffs2 -o rw,compr=none mtd:boot /${KERNEL_IMAGEDEST} || mount -t jffs2 -o rw,compr=none mtd:'boot partition' /${KERNEL_IMAGEDEST}
        fi
    fi
}
pkg_prerm_kernel-image() {
    if [ -z "$D" ]
    then
        if mountpoint -q /${KERNEL_IMAGEDEST}
        then
            mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
        else
            mount -t jffs2 -o rw,compr=none mtd:boot /${KERNEL_IMAGEDEST}
        fi
    fi
}
pkg_postinst_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then
                if grep -q '\<root=/dev/mtdblock3\>' /proc/cmdline && grep -q '\<root=ubi0:rootfs\>' /boot/autoexec.bat; then
                        sed -ie 's!${CMDLINE_UBI}!${CMDLINE_JFFS2}!' /boot/autoexec.bat;
                fi
                umount /${KERNEL_IMAGEDEST};
        fi
}
pkg_postrm_kernel-image() {
    if [ -z "$D" ]
    then
        umount /${KERNEL_IMAGEDEST}
    fi
}

# Do not use update-alternatives!
pkg_postinst_kernel () {
}
pkg_postrm_kernel () {
}

CMDLINE_JFFS2 = "root=/dev/mtdblock3 rootfstype=jffs2 rw ${CMDLINE_CONSOLE}"
CMDLINE_UBI = "ubi.mtd=root root=ubi0:rootfs rootfstype=ubifs rw ${CMDLINE_CONSOLE}"
CMDLINE = "${@base_contains('IMAGE_FSTYPES', 'ubi.nfi', '${CMDLINE_UBI}', '${CMDLINE_JFFS2}', d)}"
USB_CMDLINE = "root=${USB_ROOT} rootdelay=10 rw ${CMDLINE_CONSOLE}"

LOCALVERSION = "-${MACHINE}"
