SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
KV = "3.9.7-r2"

inherit kernel machine_kernel_pr

SRCDATE = "20141120"

SRC_URI[md5sum] = "dccfbe420bbc64291ab87b0545475841"
SRC_URI[sha256sum] = "c8c139148e099ed3ec88103f7d2dbd0420ec6156b4ac9adc4b7e6d7d955f456e"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".1"

SRC_URI = "http://en3homeftp.net/pub/src/linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://dmx_demux.patch \
    file://fix_fuse_for_linux_mips_3-9.patch \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://linux-3.9.7-gcc-4.9.3-build-error-fixed.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    ${@bb.utils.contains("DISTRO_NAME", "openvix", "file://dmx_delete_e2stb.patch", "", d)} \
    file://sit2_op.o \
    file://kernel-add-support-for-gcc-5.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://0001-Support-TBS-USB-drivers-3.9.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://blindscan2.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
    "

S = "${WORKDIR}/linux-${KV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

do_configure_prepend() {
    install -d ${B}/drivers/media/dvb-frontends
    cp -rf ${WORKDIR}/sit2_op.o ${B}/drivers/media/dvb-frontends/sit2_op.o
}

kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
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
