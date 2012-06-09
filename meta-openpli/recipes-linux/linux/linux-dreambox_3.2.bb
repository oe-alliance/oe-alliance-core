PR = "${INC_PR}.18"

PATCHREV = "b299a6a132d842b074b289b2568eece452d0663c"
PATCHLEVEL = "19"

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
			file://make-3.82-hack.patch \
			file://load-average-calculation-fix.patch \
			file://jffs2-compression-fixes.patch \
			file://defconfig \
"

SRC_URI[kernel.md5sum] = "7ceb61f87c097fc17509844b71268935"
SRC_URI[kernel.sha256sum] = "c881fc2b53cf0da7ca4538aa44623a7de043a41f76fd5d0f51a31f6ed699d463"
SRC_URI[kernel-patch.md5sum] = "34c605cba1dac636e81e14212380dd32"
SRC_URI[kernel-patch.sha256sum] = "6c269bf9f899c4bd4d03b010719d995d5c9f04c3416cebda2796f94c563737e4"
SRC_URI[dmm-patch.md5sum] = "c364975ed4c2d066634729827f8552b9"
SRC_URI[dmm-patch.sha256sum] = "e56c75ad2c8e1d9328d55a7abf7c7ce805acb96354eb26449d5f91c65ad340a4"
SRC_URI[unionfs.md5sum] = "06e7c9f6cafd49b72184be851116c511"
SRC_URI[unionfs.sha256sum] = "ce6ffa3c17a11dcca24196c11f6efc95c59b65a5b99958e73e8d4cc8e4b1f1ef"

S = "${WORKDIR}/linux-3.2"

require linux-dreambox.inc
