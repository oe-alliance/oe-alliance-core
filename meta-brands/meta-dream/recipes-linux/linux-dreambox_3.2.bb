SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PRECOMPILED_ARCH = "${MACHINE}"
PRECOMPILED_ARCH_dm7020hdv2 = "dm7020hd"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = ".1"

PATCHREV = "3c7230bc0819495db75407c365f4d1db70008044"
PATCHLEVEL = "68"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.2.tar.bz2;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.2.${PATCHLEVEL}.xz;apply=yes;name=kernel-patch \
    http://sources.dreamboxupdate.com/download/kernel-patches/${P}-${PATCHREV}.patch.bz2;name=dmm-patch \
    http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_3.2.2.diff.gz;name=unionfs \
    file://clear_sublevel.patch \
    file://0001-Revert-MIPS-Fix-potencial-corruption.patch \
    file://fadvise_dontneed_change.patch \
    file://fix-proc-cputype.patch \
    file://mips-refactor-clearpage-and-copypage.patch \
    file://rtl8712-backport-b.patch \
    file://rtl8712-backport-c.patch \
    file://rtl8712-backport-d.patch \
    file://make-3.82-hack.patch \
    file://0001-brmcnand_base-disable-flash-BBT-on-64MB-nand.patch \
    file://0002-ubifs-add-config-option-to-use-zlib-as-default-compr.patch \
    file://em28xx_fix_terratec_entries.patch \
    file://em28xx_add_terratec_h5_rev3.patch \
    file://dvb-usb-siano-always-load-smsdvb.patch \
    file://dvb-usb-af9035.patch \
    file://dvb-usb-a867.patch \
    file://dvb-usb-rtl2832.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://dvb-usb-smsdvb_fix_frontend.patch \
    file://0001-it913x-backport-changes-to-3.2-kernel.patch \
    file://0001-correctly-initiate-nand-flash-ecc-config-when-old-2n.patch \
    file://rtl8712-fix-warnings.patch \
    file://fixme-hardfloat.patch \
    file://add-mnt-part.patch \
    file://defconfig \
"

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

SRC_URI[kernel.md5sum] = "7ceb61f87c097fc17509844b71268935"
SRC_URI[kernel.sha256sum] = "c881fc2b53cf0da7ca4538aa44623a7de043a41f76fd5d0f51a31f6ed699d463"
SRC_URI[kernel-patch.md5sum] = "8ba205b73dcd6aa6748d916af294b6f0"
SRC_URI[kernel-patch.sha256sum] = "77368e2ab9d8d9282ff6e00973fe0ba7948e6b519f2efcab3b008c59526f1bd3"
SRC_URI[dmm-patch.md5sum] = "9bce4d986a4bfcccdc4b2fecd849269d"
SRC_URI[dmm-patch.sha256sum] = "8914df36eb1f6a270d2b32c46d93cb81bbaae02604fba6135a9b1509e1ec1d84"
SRC_URI[unionfs.md5sum] = "06e7c9f6cafd49b72184be851116c511"
SRC_URI[unionfs.sha256sum] = "ce6ffa3c17a11dcca24196c11f6efc95c59b65a5b99958e73e8d4cc8e4b1f1ef"

S = "${WORKDIR}/linux-3.2"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/boot"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_install_append() {
        ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        gzip -9 ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
        echo "/boot/bootlogo-${PRECOMPILED_ARCH}.elf.gz filename=/boot/bootlogo-${PRECOMPILED_ARCH}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
        echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
}

FILES_kernel-image += "${KERNEL_IMAGEDEST}/autoexec*.bat"
FILES_kernel-vmlinux = "/boot/vmlinux-${KERNEL_VERSION}*"

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
CMDLINE = "${@base_contains('IMAGE_FSTYPES', 'ubinfi', '${CMDLINE_UBI}', '${CMDLINE_JFFS2}', d)}"

do_rm_work() {
}