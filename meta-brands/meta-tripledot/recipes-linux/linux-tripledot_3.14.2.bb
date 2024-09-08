SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "3.14.2"
SRCDATE:jj7362 = "20141208"
SRCDATE:vg5000 = "20141208"
SRCDATE:vg1000 = "20141208"
SRCDATE:vg2000 = "20141208"
SRCDATE:yh7362 = "20160217"
SRCDATE:yh62tc = "20170821"

COMPATIBLE_MACHINE = "^(jj7362|vg1000|vg2000|vg5000|yh62tc|yh7362)$"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = ".4"

SRC_URI[jj7362.md5sum] = "8e0385481057a214f0635c8b947dbb7d"
SRC_URI[jj7362.sha256sum] = "6c782f1003a48c508832660b1053d68f3c616f5b1ece373d06125a0e7f47d23a"
SRC_URI[vg5000.md5sum] = "8e0385481057a214f0635c8b947dbb7d"
SRC_URI[vg5000.sha256sum] = "6c782f1003a48c508832660b1053d68f3c616f5b1ece373d06125a0e7f47d23a"
SRC_URI[vg2000.md5sum] = "8e0385481057a214f0635c8b947dbb7d"
SRC_URI[vg2000.sha256sum] = "6c782f1003a48c508832660b1053d68f3c616f5b1ece373d06125a0e7f47d23a"
SRC_URI[vg1000.md5sum] = "58ab26c0ae0c791ce7e962f7f1ae4c3c"
SRC_URI[vg1000.sha256sum] = "d53df9471aea4ae35644d96ef8c8425ad82001e0de8dc5b9fdac8f4b983d0232"
SRC_URI[yh7362.md5sum] = "23781bce0ab453e7c18092f1f6dd2e2b"
SRC_URI[yh7362.sha256sum] = "bf31e5b1c6e9295bdcd531b94a0f327f70b07bc901cd68dbc1730f04274a86d4"
SRC_URI[yh62tc.md5sum] = "23781bce0ab453e7c18092f1f6dd2e2b"
SRC_URI[yh62tc.sha256sum] = "bf31e5b1c6e9295bdcd531b94a0f327f70b07bc901cd68dbc1730f04274a86d4"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}-base/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/tripledot/${MACHINE}-linux-${PV}-base-${SRCDATE}.tgz;name=${MACHINE} \
        file://defconfig \
        file://add-rt2x00-wifi-devices.patch \
        file://add-rtl8192cu-wifi-devices.patch \
        file://add-dmx-source-timecode.patch \
        file://af9015-output-full-range-SNR.patch \
        file://af9033-output-full-range-SNR.patch \
        file://cxd2820r-output-full-range-SNR.patch \
        file://dvb_usb_disable_rc_polling.patch \
        file://dvb-usb-dib0700-disable-sleep.patch \
        file://fix-proc-cputype.patch \
        file://iosched-slice_idle-1.patch \
        file://it913x-switch-off-PID-filter-by-default.patch \
        file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
        file://tda18271-advertise-supported-delsys.patch \
        file://timedate.patch \
        file://linux-3.14.2-gcc-4.9.3-build-error-fixed.patch \
        file://kernel-add-support-for-gcc-5.patch \
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
        file://rtl8712-fix-warnings.patch \
        file://0001-Support-TBS-USB-drivers.patch \
        file://0001-STV-Add-PLS-support.patch \
        file://0001-STV-Add-SNR-Signal-report-parameters.patch \
        file://0001-stv090x-optimized-TS-sync-control.patch \
        file://blindscan2.patch \
        file://genksyms_fix_typeof_handling.patch \
        file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
        file://0003-cp1emu-do-not-use-bools-for-arithmetic.patch \
        file://0004-makefile-disable-warnings.patch \
        file://move-default-dialect-to-SMB3.patch \
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

S = "${WORKDIR}/linux-${PV}-base"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

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
