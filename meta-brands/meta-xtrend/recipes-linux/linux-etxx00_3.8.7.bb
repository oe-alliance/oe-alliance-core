SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "2"
KERNEL_RELEASE = "3.8.7"

COMPATIBLE_MACHINE = "^(et5x00|et6x00|et9x00)$"

SRC_URI[md5sum] = "5f6aaac90a4587df34e418bedd7d40eb"
SRC_URI[sha256sum] = "afc3e654b779f4b994a0d455d6ad12f46ff0dbec2fe222a4f55925744b498218"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/xtrend/xtrend-linux-${PV}.tar.gz \
    file://defconfig \
    file://Kernelupdate13.patch \
    file://0001-kernel-add-support-for-gcc-5.patch \
    file://0001-Revert-default-authentication-needs-to-be-at-least-n.patch \
    file://0001-Revert-MIPS-mm-Add-compound-tail-page-_mapcount-when.patch \
    file://0001-Revert-MIPS-Add-fast-get_user_pages.patch \
    file://0001-Revert-MIPS-Fix-potencial-corruption.patch \
    file://add-dmx-source-timecode.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://as102-adjust-signal-strength-report.patch \
    file://as102-scale-MER-to-full-range.patch \
    file://cinergy_s2_usb_r2.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://dvb-usb-rtl2832.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://em28xx_add_terratec_h5_rev3.patch \
    file://fix-proc-cputype.patch \
    file://fixme-hardfloat.patch \
    file://iosched-slice_idle-1.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://dvb-usb-siano-always-load-smsdvb-v2.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://nfs-max-rwsize-8k.patch \
    file://0001-rt2800usb-add-support-for-rt55xx.patch \
    file://rtl28xxu-and-tuners-update-to-latest-version-09-09-2013.patch \
    file://rtl2832-scale-SNR-to-full-range.patch \
    file://rtl8712-fix-warnings.patch \
    file://rtl8187se-fix-warnings.patch \
    file://em28xx-dvb-enable-LNA-by-default-for-PCTV290e.patch \
    file://zl10353-output-full-range-SNR.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://kernel-add-support-for-gcc9.patch \
    file://kernel-add-support-for-gcc10.patch \
    file://kernel-add-support-for-gcc11.patch \
    file://kernel-add-support-for-gcc12.patch \
    file://kernel-add-support-for-gcc13.patch \
    file://kernel-add-support-for-gcc14.patch \
    file://build-with-gcc12-fixes.patch \
    file://stv0900-Multistream-support.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://2-2-stv090x-on-tuning-lock-return-correct-tuned-paramaters-like-freq-sr-fec-rolloff-etc.patch \
    file://0001-Support-TBS-USB-drivers.patch \
    file://genksyms_fix_typeof_handling.patch \
    file://0002-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0003-log2-give-up-on-gcc-constant-optimizations.patch \
    file://add-attributes-fix-modules-compile.patch \
    file://makefile-silence-warnings.patch \
    file://move-default-dialect-to-SMB2.patch \
    file://linux3.4-ARM-8933-1-replace-Sun-Solaris-style-flag-on-section.patch \
    file://fix-build-with-binutils-2.41.patch \
    "

export KCFLAGS = " -Wno-error=incompatible-pointer-types \
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

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

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
