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
    file://0001-linuxtv-api-DMM-drivers-are-now-ready-for-linux-tv-a.patch;apply=no \
    file://0001-correctly-initiate-nand-flash-ecc-config-when-old-2n.patch \
    file://defconfig \
"

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

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "${@base_contains('MACHINE_FEATURES', 'usbconsole', 'ttyS0,115200', 'null', d)}"

require linux-dreambox.inc

do_rm_work() {
}