PR = "${INC_PR}.16"

PATCHREV = "f938a40edaade30923c3b9203ff6a4f54c452f11"
PATCHLEVEL = "18"

SRC_URI = " \
			${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.2.tar.bz2;name=kernel \
			${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.2.${PATCHLEVEL}.bz2;apply=yes;name=kernel-patch \
			http://sources.dreamboxupdate.com/download/kernel-patches/${P}-${PATCHREV}.patch.bz2;name=dmm-patch \
			http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_3.2.2.diff.gz;name=unionfs \
			file://clear_sublevel.patch \
			file://fadvise_dontneed_change.patch \
			file://fix-proc-cputype.patch \
			file://rtl8712-backport-b.patch \
			file://rtl8712-backport-c.patch \
			file://rtl8712-backport-d.patch \
			file://sata_fixes.patch \
			file://load-average-calculation-fix.patch \
			file://make-3.82-hack.patch \
			file://defconfig \
"

SRC_URI[kernel.md5sum] = "7ceb61f87c097fc17509844b71268935"
SRC_URI[kernel.sha256sum] = "c881fc2b53cf0da7ca4538aa44623a7de043a41f76fd5d0f51a31f6ed699d463"
SRC_URI[kernel-patch.md5sum] = "b5b3a5e0374e6ab87a2cd6963268f3c9"
SRC_URI[kernel-patch.sha256sum] = "ee113630361297bdf5ab44cd5b9eba72800d7a711915d7b779ba484d0a018085"
SRC_URI[dmm-patch.md5sum] = "1410c4c94fe1d494f823806a9b0bb8e0"
SRC_URI[dmm-patch.sha256sum] = "e8f92205cacab575aae1a2e1bc8571a7b1cc1fb454d16104f6f6585a9580f462"
SRC_URI[unionfs.md5sum] = "06e7c9f6cafd49b72184be851116c511"
SRC_URI[unionfs.sha256sum] = "ce6ffa3c17a11dcca24196c11f6efc95c59b65a5b99958e73e8d4cc8e4b1f1ef"

S = "${WORKDIR}/linux-3.2"

require linux-dreambox.inc
