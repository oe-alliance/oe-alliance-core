DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
MACHINE_KERNEL_PR_append = ".13"

DEPENDS = "genromfs-native gcc"
DEPENDS_azboxhd = "genromfs-native azbox-hd-buildimage gcc "
DEPENDS_azboxminime = "genromfs-native azbox-minime-packer gcc"


KV = "3.3.1"
SRCDATE = "14052013"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"
SRC_URI += "http://azbox-enigma2-project.googlecode.com/files/linux-azbox-${KV}-new-2.tar.bz2;name=azbox-kernel \
	   file://defconfig \
	   file://genzbf.c \
	   file://sigblock.h \
	   file://zboot.h \
	   file://emhwlib_registers_tango2.h \
	   file://sata.patch \
	   "

SRC_URI_append_azboxhd += "http://azbox-enigma2-project.googlecode.com/files/initramfs-${MACHINE}-oe-core-${SRCDATE}.tar.bz2;name=azbox-initrd-${MACHINE} \
	   file://hdide.patch \
	   "

SRC_URI_append_azboxme = "http://azbox-enigma2-project.googlecode.com/files/initramfs-${MACHINE}-oe-core-${SRCDATE}.tar.bz2;name=azbox-initrd-${MACHINE}"

SRC_URI_append_azboxminime = "http://azbox-enigma2-project.googlecode.com/files/initramfs-${MACHINE}-oe-core-${SRCDATE}.tar.bz2;name=azbox-initrd-${MACHINE}"


SRC_URI[azbox-kernel.md5sum] = "dfd04abeaf3741b3d2a44428ca5aeaa1"
SRC_URI[azbox-kernel.sha256sum] = "31b73397220d85aedf3c914026371fc1eeac67e3de09a5610b70b209d2a8b9df"
SRC_URI[azbox-initrd-azboxhd.md5sum] = "1c788f63ec2397064b70cf542888389a"
SRC_URI[azbox-initrd-azboxhd.sha256sum] = "f5804ec6226fe9f2ba543aa1dcc457d134928eceb5e5e247bcdeeb61b1cc7cf9"
SRC_URI[azbox-initrd-azboxme.md5sum] = "6b49d5de3533eb73d753b353eb8e0121"
SRC_URI[azbox-initrd-azboxme.sha256sum] = "09d63650d18337a2b8a8ba7c9d33d2a1b939152f25990e3bfc3166eb5c1c4040"
SRC_URI[azbox-initrd-azboxminime.md5sum] = "f9686a2373d3966f531ab783e41a2d80"
SRC_URI[azbox-initrd-azboxminime.sha256sum] = "122a9f7e8b368b47e74eb8451d2dd856bed80dbe7e23c35cca63cb95dded891d"

S = "${WORKDIR}/linux-${KV}"

inherit kernel

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "zbimage-linux-xload"
KERNEL_IMAGETYPE = "zbimage-linux-xload"
KERNEL_IMAGEDEST = "/tmp"


FILES_kernel-image = "/boot/zbimage-linux-xload"

CFLAGS_prepend = "-I${WORKDIR} "

do_configure_prepend() {
	oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
	oe_runmake oldconfig
}

kernel_do_compile() {
	gcc ${CFLAGS} ${WORKDIR}/genzbf.c -o ${WORKDIR}/genzbf
	
	install -m 0755 ${WORKDIR}/genzbf ${S}/arch/mips/boot/

	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
	oe_runmake include/linux/version.h CC="${KERNEL_CC}" LD="${KERNEL_LD}"
	oe_runmake ${KERNEL_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${AR}" OBJDUMP="${OBJDUMP}" NM="${NM}"
	oe_runmake modules CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${AR}" OBJDUMP="${OBJDUMP}"
}

do_install_append () {
	install -d ${D}/boot
	install -m 0644 ${S}/arch/mips/boot/zbimage-linux-xload ${D}/boot/zbimage-linux-xload

}
