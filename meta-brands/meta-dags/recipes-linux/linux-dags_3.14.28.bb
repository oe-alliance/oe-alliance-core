SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

KV = "3.14.28"
MACHINE_KERNEL_PR = "r12"

COMPATIBLE_MACHINE = "dags7252"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[md5sum] = "3b6d3fd2257b61789eebdebac5c597b2"
SRC_URI[sha256sum] = "eb56d7e99ab9e869b6abfb2a0463015e7d7b2e8610b7b9d05285edb8e8dfaf4f"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/dags/linux-3.14.28.tar.xz \
    file://defconfig \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_FIX_EXTABLE} \
    file://date-time.patch \
    file://0001.remove_vtuner_index_check.patch \
    ${KERNEL_PATCH_TBS_USB_314A} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://genksyms_fix_typeof_handling.patch \
    ${KERNEL_PATCHES_DVB_TDA18273} \
    ${KERNEL_PATCHES_DVB_SI2157_OLD} \
    ${KERNEL_PATCHES_DVB_SI2168_OLD} \
    file://0003-cxusb-Geniatech-T230-support.patch \
    file://CONFIG_DVB_SP2.patch \
    file://dvbsky.patch \
    file://rtl2832u-2.patch \
    ${KERNEL_PATCH_MISC_FIX_LINKER} \
    ${KERNEL_PATCH_BINUTILS241_V1} \
    ${KERNEL_PATCH_WIFI_EXTAUTH_314} \
"

export KCFLAGS = " -std=gnu17 \
                   -Wno-error=incompatible-pointer-types \
                   -Wno-error=address-of-packed-member \
                   -Wno-error=unused-result \
                   -Wno-error=format-overflow \
                   -Wno-error=stringop-overflow \
                   -Wno-error=unused-variable \
                   -Wno-error=int-conversion \
                   -Wno-error=array-parameter \
                   -Wno-error=unused-function \
                   -Wno-error=stringop-overread \
                   -Wno-error=unused-const-variable \
                   -Wno-error=maybe-uninitialized \           
"

S = "${UNPACKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p1
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm:kernel-image () {
}

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-dags-${KV}:"

do_rm_work() {
}
