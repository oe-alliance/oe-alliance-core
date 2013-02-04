DESCRIPTION = "Linux kernel for ${MACHINE}"
DESCRIPTION = "Linux kernel for EBox ${MACHINE}"
LICENSE = "GPL"
SECTION = "kernel"

KERNEL="2.6.18-7.3"
MACHINE_KERNEL_PR_append = ".4"

SRC_URI[kernel.md5sum] = "3f1faf26f103724e86560baa8dfdf497"
SRC_URI[kernel.sha256sum] = "91d9f753ff73401c9cda9efa0e40372c0fd40ffd366eac9da7981d40920765b2"
SRC_URI[unionfs.md5sum] = "c0c838b717f98a19a09483fb10e7299e"
SRC_URI[unionfs.sha256sum] = "b2e04936254bbf778c963de862061027c858a2e157bb2e48c773d2ed2445282e"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "http://archiv.mixos-support.com/${MACHINE}-linux-${KERNEL}.tar.bz2;name=kernel \
	http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_2.6.18.8.diff.gz;name=unionfs \
	file://defconfig \
	file://linux-2.6.18-fix-mips-crosscompile.patch \
	file://linux-2.6.18-fix-proc-cputype.patch \
	file://stblinux-2.6.18-libata-revert-no-more-needed-change.patch \
	file://stblinux-2.6.18-libata-hdd-spinup-workaround.patch \
	file://kbuild-fix-make-incompatibility.patch \
	file://0001-MIPS-Fix-possible-hang-in-LL-SC-futex-loops.patch \
	file://0001-Add-support-for-FTDI-FT4232H-based-devices.patch \
	file://0001-proc-mounts_poll-make-consistent-to-mdstat_poll.patch \
	file://0001-fixed-broken-usb-with-gcc-4.6.x.patch \
	file://linux-2.6.18-include-asm.patch \
	file://linux-2.6.18-include-linux.patch \
	file://linux-2.6.18-mod_devicetable_h.patch \
	file://nfs-max-rwsize-8k.patch \
	"

S = "${WORKDIR}/linux-${PV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

do_configure_prepend() {
	oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
	oe_runmake oldconfig
}

do_install_append() {
        cp include/asm/asm-offsets.h $kerneldir/include/asm/asm-offsets.h
        cp scripts/basic/fixdep $kerneldir/scripts/basic/fixdep
        cp scripts/mod/modpost $kerneldir/scripts/mod/modpost
}

kernel_do_install_append() {
	${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
}

pkg_postinst_kernel-image () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/mtd2 0 0
			nandwrite -p /dev/mtd2 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	true
}
