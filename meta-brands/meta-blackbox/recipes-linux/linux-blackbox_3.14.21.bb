SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel machine_kernel_pr kernel-fixups

KV = "3.14.21"

COMPATIBLE_MACHINE = "blackbox7405"

SRCDATE = "20151003"

SRC_URI[md5sum] = "f7edd5923ead4c334840c62eab3e79f0"
SRC_URI[sha256sum] = "05c7315ec1703db9598f641b9ee5483218f89e207217e5b9c7d52300d123d88e"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r3"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/unibox/linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://linux-3.14.21-gcc-4.9.3-build-error-fixed.patch \
    file://rtl8712-fix-warnings.patch \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_FIX_EXTABLE} \
    ${KERNEL_PATCH_TBS_USB_314A} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://genksyms_fix_typeof_handling.patch \
    file://0002-cp1emu-do-not-use-bools-for-arithmetic.patch \
    ${KERNEL_PATCH_BINUTILS241_V1} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
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

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure:prepend() {
    rm -rf ${STAGING_KERNEL_DIR}/.cofig
    rm -rf ${STAGING_KERNEL_DIR}/.config
    rm -rf ${STAGING_KERNEL_DIR}/.config.old
    rm -rf ${STAGING_KERNEL_DIR}/include/generated
    rm -rf ${STAGING_KERNEL_DIR}/include/config
    rm -rf ${STAGING_KERNEL_DIR}/arch/mips/include/generated
    install -d ${B}/usr
    install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/${MTD_KERNEL} 0 0
            nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
