PR = "${INC_PR}.13"

PATCHREV = "a3eaf43fbd1c2994fb61fd7065724ff4f2e4f1e0"
PATCHLEVEL = "16"

SRC_URI = " \
			${KERNELORG_MIRROR}/linux/kernel/v3.x/linux-3.2.tar.bz2;name=kernel \
			${KERNELORG_MIRROR}/linux/kernel/v3.x/patch-3.2.${PATCHLEVEL}.bz2;apply=yes;name=kernel-patch \
			http://sources.dreamboxupdate.com/download/kernel-patches/${P}-${PATCHREV}.patch.bz2;name=dmm-patch \
			http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_3.2.2.diff.gz;name=unionfs \
			file://clear_sublevel.patch \
			file://fadvise_dontneed_change.patch \
			file://fix-proc-cputype.patch \
			file://threaded-interrupt-support.patch \
			file://brcmnand-buildfix.patch \
			file://brcmnand-fixed-corr-uncorr-error-detection.patch \
			file://brcmnand-fixed-non-hwecc-first-workaround.patch \
			file://rtl8712-backport-a.patch \
			file://rtl8712-backport-b.patch \
			file://rtl8712-backport-c.patch \
			file://rtl8712-backport-d.patch \
			file://make-3.82-hack.patch \
			file://defconfig \
"

SRC_URI[kernel.md5sum] = "7ceb61f87c097fc17509844b71268935"
SRC_URI[kernel.sha256sum] = "c881fc2b53cf0da7ca4538aa44623a7de043a41f76fd5d0f51a31f6ed699d463"
SRC_URI[kernel-patch.md5sum] = "1179d169e0ec15ada29cfa9066777a7f"
SRC_URI[kernel-patch.sha256sum] = "ea412451426f39b4b0edd89ce592e7f21a87c5bc12ed2600ad45c3f71867f415"
SRC_URI[dmm-patch.md5sum] = "29e0428508845663144ea8ed26575054"
SRC_URI[dmm-patch.sha256sum] = "a725831642a8d19a9664592adf36ab7a1341cca0a3570f1c02e5e449cbf2738f"
SRC_URI[unionfs.md5sum] = "06e7c9f6cafd49b72184be851116c511"
SRC_URI[unionfs.sha256sum] = "ce6ffa3c17a11dcca24196c11f6efc95c59b65a5b99958e73e8d4cc8e4b1f1ef"

S = "${WORKDIR}/linux-3.2"

require linux-dreambox.inc
