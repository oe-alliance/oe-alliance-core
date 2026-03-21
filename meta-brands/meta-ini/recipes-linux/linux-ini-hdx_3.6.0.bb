SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KV = "3.6.0"
SRCDATE = "20140610"

COMPATIBLE_MACHINE = "inihdx"

SRC_URI[md5sum] = "3a2b0f1df094019e07290e85e9ba700e"
SRC_URI[sha256sum] = "a2e7e6a3b9344412e33855372a71f6c1f2e12a598ca8c8cc6b1b0a929ae698b0"

inherit kernel machine_kernel_pr kernel-fixups

MACHINE_KERNEL_PR = "r2"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/ini/bcm7413-linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://mtd_nor_nand.patch \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    ${KERNEL_PATCH_MIPS_REVERT_COMPOUND} \
    ${KERNEL_PATCH_MIPS_REVERT_FAST_GUP} \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://cinergy_s2_usb_r2.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb-usb-a867.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb-usb-rtl2832.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://em28xx_add_terratec_h5_rev3.patch \
    file://fix-proc-cputype.patch \
    ${KERNEL_PATCH_MIPS_HARDFLOAT} \
    file://iosched-slice_idle-1.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://fix-dvb-siano-sms-order.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    ${KERNEL_PATCH_DVB_MULTISTREAM_36} \
    ${KERNEL_PATCH_MISC_TIMECONST_PERL5} \
    file://genksyms_fix_typeof_handling.patch \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0004-makefile-disable-warnings.patch \
    ${KERNEL_PATCH_FIX_DEVINITDATA} \
    ${KERNEL_PATCH_BINUTILS241_V1} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
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
                   -Wno-error=implicit-int \
                   -Wno-error=declaration-missing-parameter-type \
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_configure:prepend() {
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
