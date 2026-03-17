SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KV = "3.14.2"
SRCDATE = "20150603"

COMPATIBLE_MACHINE = "inihdp"

SRC_URI[md5sum] = "0a9eb4bab291ea6047a0c779c0dd4304"
SRC_URI[sha256sum] = "81effcef755b6b0fe5a930b5bfc058ea1f5195b410819df1585dc52aff0f6d8c"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR = "r2"

inherit kernel machine_kernel_pr kernel-fixups

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/ini/bcm7425-linux-${KV}-${SRCDATE}.tgz \
    file://defconfig \
    ${KERNEL_PATCHES_SERIES_3_14_INI} \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://iosched-slice_idle-1.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://rtl8712-fix-warnings.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch  \
    file://0004-makefile-disable-warnings.patch \
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
"

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

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
