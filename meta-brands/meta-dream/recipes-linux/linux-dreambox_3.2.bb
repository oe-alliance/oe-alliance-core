inherit machine_kernel_pr

MACHINE_KERNEL_PR_append = ".4"

PATCHREV = "4e0356d04e89df800361b9252f990716f5523c6e"
PATCHLEVEL = "59"

SRC_URI = " \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.2.tar.bz2;name=kernel \
    ${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.2.${PATCHLEVEL}.xz;apply=yes;name=kernel-patch \
    http://sources.dreamboxupdate.com/download/kernel-patches/${P}-${PATCHREV}.patch.bz2;name=dmm-patch \
    http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_3.2.2.diff.gz;name=unionfs \
    file://clear_sublevel.patch \
    file://fadvise_dontneed_change.patch \
    file://fix-proc-cputype.patch \
    file://mips-refactor-clearpage-and-copypage.patch \
    file://rtl8712-backport-b.patch \
    file://rtl8712-backport-c.patch \
    file://rtl8712-backport-d.patch \
    file://make-3.82-hack.patch \
    file://0001-SCSI-sd-Use-SCSI-read-write-16-with-32-bit-LBA-drive.patch \
    file://0002-add-crypto-api-xz-support.patch \
    file://0003-add-XZ-compression-support-to-UBIFS.patch \
    file://0004-block2mtd-add-possibility-to-change-the-writesize.patch \
    file://0005-block2mtd-add-possibility-to-remove-block2mtd-device.patch \
    file://0006-mtd-block2mtd-fix-recursive-call-of-mtd_writev.patch \
    file://0007-mtd-block2mtd-throttle-writes-by-calling-balance_dir.patch \
    file://0008-The-ubi-maintained-flag-must-be-set-earlier-to-preve.patch \
    file://0009-fixed-partition-is-ubi-maintained-check.patch \
    file://0001-add-memory-mapping-support-to-usbfs-used-by-sundtek-.patch \
    file://0001-linuxtv-api-DMM-drivers-are-now-ready-for-linux-tv-a.patch \	
    file://0001-brmcnand_base-disable-flash-BBT-on-64MB-nand.patch \
    file://0002-ubifs-add-config-option-to-use-zlib-as-default-compr.patch \
    file://em28xx_fix_terratec_entries.patch \
    file://em28xx_add_terratec_h5_rev3.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://dvb-usb-af9035.patch \
    file://dvb-usb-a867.patch \
    file://dvb-usb-rtl2832.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://defconfig \
"

SRC_URI_append_dm800sev2 = " file://0001-add-support-for-DM800SEv2-and-DM500HDv2-simplified-1.patch"
SRC_URI_append_dm500hdv2 = " file://0001-add-support-for-DM800SEv2-and-DM500HDv2-simplified-1.patch"

SRC_URI[kernel.md5sum] = "7ceb61f87c097fc17509844b71268935"
SRC_URI[kernel.sha256sum] = "c881fc2b53cf0da7ca4538aa44623a7de043a41f76fd5d0f51a31f6ed699d463"
SRC_URI[kernel-patch.md5sum] = "68696787e651422f02816d2c825f9a9c"
SRC_URI[kernel-patch.sha256sum] = "9dcb2e477923c8d1f662f2e8431a0ae06d0f13b6383fde30a051a3361ccd26b7"
SRC_URI[dmm-patch.md5sum] = "d17d65e9978343d540e0b60767a82286"
SRC_URI[dmm-patch.sha256sum] = "576356545de7f587d164d1cee2cb17b6c1ce3efbe2e01ff785c13ec2d544d220"
SRC_URI[unionfs.md5sum] = "06e7c9f6cafd49b72184be851116c511"
SRC_URI[unionfs.sha256sum] = "ce6ffa3c17a11dcca24196c11f6efc95c59b65a5b99958e73e8d4cc8e4b1f1ef"

S = "${WORKDIR}/linux-3.2"

require linux-dreambox.inc
