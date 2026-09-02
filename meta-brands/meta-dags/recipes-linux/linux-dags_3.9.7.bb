SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"
KV = "3.9.7-r2"

inherit kernel machine_kernel_pr kernel-fixups

COMPATIBLE_MACHINE = "^(dags7335|dags7356|dags7362)$"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRCDATE = "20141120"

SRC_URI[md5sum] = "dccfbe420bbc64291ab87b0545475841"
SRC_URI[sha256sum] = "c8c139148e099ed3ec88103f7d2dbd0420ec6156b4ac9adc4b7e6d7d955f456e"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r2"

SRC_URI = "https://source.mynonpublic.com/dags/linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://dmx_demux.patch \
    ${KERNEL_PATCH_MIPS_FUSE_FIX} \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://linux-3.9.7-gcc-4.9.3-build-error-fixed.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "file://dmx_delete_e2stb.patch", "", d)} \
    file://sit2_op.o \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_FIX_EXTABLE} \
    ${KERNEL_PATCH_TBS_USB_39} \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    file://genksyms_fix_typeof_handling.patch \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
    ${KERNEL_PATCH_BINUTILS241_V1} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    ${KERNEL_PATCH_WIFI_EXTAUTH_39} \
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

S = "${UNPACKDIR}/linux-${KV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure:prepend() {
    install -d ${B}/drivers/media/dvb-frontends
    cp -rf ${UNPACKDIR}/sit2_op.o ${B}/drivers/media/dvb-frontends/sit2_op.o
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
