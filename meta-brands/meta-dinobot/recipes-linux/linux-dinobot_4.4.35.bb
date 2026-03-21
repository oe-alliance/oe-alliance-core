DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.35"
SRCDATE:u5pvr = "20180502"
SRCDATE = "20180828"

COMPATIBLE_MACHINE = "^(u41|u42|u43|u45|u5|u51|u52|u53|u54|u55|u56|u57|u532|u533|u571|u5pvr)$"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR:append = "33"

SRCREV_FORMAT = "kernel_wireguard"

SRC_URI[kernel.md5sum] = "bd22f82d08a5feb4f1360d5739919ee0"
SRC_URI[kernel.sha256sum] = "df83207ddfe34ac41a55e5e42eaae9c3ac3c4ef0750c786886719a33bf08b617"

SRC_URI[new.md5sum] = "f0dd43d5adc013d0dd89061e3249855a"
SRC_URI[new.sha256sum] = "32a8caabfba94d81b649de8dd62cc5b02e1d750cad8d2676e98e242a944273a3"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI:u5pvr += "https://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz;name=kernel \
    file://initramfs-chkroot.cpio.xz;unpack=0 \
    file://initramfs-nand-arm.cpio.xz;unpack=0 \
    file://initramfs-arm-a9-nand.cpio.xz;unpack=0 \
    file://defconfig \
    file://sdio-platform.patch \
    file://accelmem.patch \
    file://cma.patch \
    file://ahci-clock.patch \
    ${@bb.utils.contains('SOC_FAMILY', 'hisi3798mv200', 'file://led.patch' , '', d)} \
    file://0004-makefile-disable-warnings.patch \
    file://0005-kallsyms-allow-bigger-ksym_name_len.patch \
    ${KERNEL_PATCH_FIX_ATTRIBUTES_GCC9} \
    ${KERNEL_PATCH_FIX_LOG2} \
    ${KERNEL_PATCH_BINUTILS241_V3} \
    ${KERNEL_PATCH_WIFI_CFG80211} \
"

SRC_URI = "https://source.mynonpublic.com/dinobot/dinobot-linux-${PV}-${SRCDATE}.tar.gz;name=new \
    file://initramfs-chkroot.cpio.xz;unpack=0 \
    file://initramfs-nand-arm.cpio.xz;unpack=0 \
    file://initramfs-arm-a9-nand.cpio.xz;unpack=0 \
    file://defconfig \
    file://410dts.patch \
    ${KERNEL_PATCH_BRCM_MMC_18V} \
    ${KERNEL_PATCH_WIFI_COMPAT} \
    file://0004-makefile-disable-warnings.patch \
    file://0005-kallsyms-allow-bigger-ksym_name_len.patch \
    file://cmav2.patch \
    ${KERNEL_PATCH_FIX_ATTRIBUTES_GCC9} \
    ${KERNEL_PATCH_FIX_LOG2} \
    ${KERNEL_PATCH_BINUTILS241_V3} \
    ${KERNEL_PATCH_WIFI_CFG80211} \
    file://block2mtd.patch \
"

# wireguard v1.0.20220627
SRCREV_wireguard = "18fbcd68a35a892527345dc5679d0b2d860ee004"
SRC_URI:append = "\
    git://git.zx2c4.com/wireguard-linux-compat;protocol=https;branch=master;name=wireguard;subpath=src;destsuffix=${S}/net/wireguard \
    ${KERNEL_PATCH_MISC_WG_KCONFIG} \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-chkroot.cpio.xz ${B}/
	install -m 0644 ${UNPACKDIR}/initramfs-nand-arm.cpio.xz ${B}/
	install -m 0644 ${UNPACKDIR}/initramfs-arm-a9-nand.cpio.xz ${B}/
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	if [ -e "${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko" ]; then
		rm -f ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend/hi_tuner.ko
	fi
	if [ -n "$(find ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend -maxdepth 0 -empty)" ]; then
		rm -rf ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/msp/drv/frontend
	fi
}

do_rm_work() {
}
