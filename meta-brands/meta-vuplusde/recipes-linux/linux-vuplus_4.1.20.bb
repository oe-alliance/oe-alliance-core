SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

MODULE = "linux-4.1.20"
KV = "4.1.20"

COMPATIBLE_MACHINE = "^(vuduo4klite)$"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR = "r12"

SRC_DATE = "20250410"

SRC_URI[md5sum] = "7854cbc1984e9723c7d46d6923de9295"
SRC_URI[sha256sum] = "6a97857446c41b94de5a5fc618afa68a493b7cc6f7f0bca14b880d95be7966ad"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/vuplus.de/linux/vuplus-linux-${PV}-${SRC_DATE}.tar.gz \
    file://defconfig_initrd \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://vufindkerneldevice.py \
    file://0002-linux_dvb-core.patch \
    file://0002-bcmgenet-recovery-fix.patch \
    file://0002-linux_4_1_1_9_dvbs2x.patch \
    file://0002-linux_rpmb_not_alloc.patch \
    file://0001-regmap-add-regmap_write_bits.patch \
    file://0003-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-soloHD.patch \
    file://0004-af9035-add-USB-ID-07ca-0337-AVerMedia-HD-Volar-A867.patch \
    file://0005-Add-support-for-EVOLVEO-XtraTV-stick.patch \
    ${KERNEL_PATCH_DVB_DIB8000} \
    ${KERNEL_PATCH_DVB_DIB0700} \
    ${KERNEL_PATCH_DVB_HAMA} \
    ${KERNEL_PATCH_DVB_TERRATEC_H7} \
    file://0010-media-Added-support-for-the-TerraTec-T1-DVB-T-USB-tu.patch \
    ${KERNEL_PATCH_DVB_TDA18250_41} \
    ${KERNEL_PATCH_DVB_XBOX_DIB} \
    ${KERNEL_PATCH_DVB_MN88472_LEAK} \
    ${KERNEL_PATCH_DVB_MN88472_PAREN} \
    ${KERNEL_PATCH_DVB_MN88472_NULL} \
    ${KERNEL_PATCH_DVB_MN88472_TYPO} \
    ${KERNEL_PATCH_DVB_MN88472_FINAL} \
    ${KERNEL_PATCH_DVB_WINTV_DUALHD} \
    ${KERNEL_PATCH_DVB_USB_A867} \
    ${KERNEL_PATCH_TBS_USB_41} \
    ${KERNEL_PATCH_TBS_USB_ENUM} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    ${KERNEL_PATCH_GCC_SERIES} \
    file://add-partition-specific-uevent-callbacks-for-partition-info.patch \
    ${KERNEL_PATCH_BINUTILS241_V2} \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage /${KERNEL_IMAGEDEST}/vufindkerneldevice.py"

kernel_do_configure:prepend() {
        install -d ${B}/usr
        install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
        if [ -e ${UNPACKDIR}/defconfig_initrd ]; then
            mv ${UNPACKDIR}/defconfig_initrd ${UNPACKDIR}/defconfig
        fi
}
kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${UNPACKDIR}/vufindkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" EXTRA_CFLAGS="-Wno-attribute-alias"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            python3 /${KERNEL_IMAGEDEST}/vufindkerneldevice.py
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
    true
}

pkg_postrm:kernel-image () {
}

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${KV}:"

do_rm_work() {
}
