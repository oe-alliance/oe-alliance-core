DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.35"
SRCDATE:u5pvr = "20180502"
SRCDATE = "20180828"

COMPATIBLE_MACHINE = "^(u41|u42|u43|u45|u5|u51|u52|u53|u54|u55|u56|u57|u532|u533|u571|u5pvr)$"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "32"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "bd22f82d08a5feb4f1360d5739919ee0"
SRC_URI[kernel.sha256sum] = "df83207ddfe34ac41a55e5e42eaae9c3ac3c4ef0750c786886719a33bf08b617"

SRC_URI[new.md5sum] = "f0dd43d5adc013d0dd89061e3249855a"
SRC_URI[new.sha256sum] = "32a8caabfba94d81b649de8dd62cc5b02e1d750cad8d2676e98e242a944273a3"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI:u5pvr += "https://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz;name=kernel \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://defconfig \
    file://sdio-platform.patch \
    file://accelmem.patch \
    file://cma.patch \
    file://ahci-clock.patch \
    ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'file://led.patch' , '', d)} \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0003-uaccess-dont-mark-register-as-const.patch \
    file://0004-makefile-disable-warnings.patch \
    file://0005-kallsyms-allow-bigger-ksym_name_len.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://Backport_minimal_compiler_attributes_h_to_support_GCC_9.patch \
    file://fix-build-with-binutils-2.41.patch \
"

SRC_URI = "https://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz;name=new \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://defconfig \
    file://410dts.patch \
    file://0001-mmc-switch-1.8V.patch \
    file://wifi-linux_4.4.183.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0003-uaccess-dont-mark-register-as-const.patch \
    file://0004-makefile-disable-warnings.patch \
    file://0005-kallsyms-allow-bigger-ksym_name_len.patch \
    file://cmav2.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://Backport_minimal_compiler_attributes_h_to_support_GCC_9.patch \
"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
    file://wg-kconfig.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${WORKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	if [ -e "${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko" ]; then
		rm -f ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko
	fi
}

do_rm_work() {
}
