SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"

inherit kernel machine_kernel_pr

KERNEL_RELEASE = "3.14.2"

SRC_URI[md5sum] = "3a36a72a0833fc1a0ff35c5c48cb3e8f"
SRC_URI[sha256sum] = "03b8d8f5066929d28b8210bfb422ac0d679bdb2c9cb625d51f65f646da8ecd34"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".1"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

SRC_URI += "http://archiv.openmips.com/gigablue-linux-${PV}-20141019.tgz \
    file://defconfig \
    file://add-dmx-source-timecode.patch \
    file://add-rtl8192cu-wifi-devices.patch \
    file://add-rt2x00-wifi-devices.patch \
    file://af9015-output-full-range-SNR.patch \
    file://af9033-output-full-range-SNR.patch \
    file://cxd2820r-output-full-range-SNR.patch \
    file://dvb_usb_disable_rc_polling.patch \
    file://dvb-usb-dib0700-disable-sleep.patch \
    file://fix-proc-cputype.patch \
    file://iosched-slice_idle-1.patch \
    file://it913x-switch-off-PID-filter-by-default.patch \
    file://linux-bcm_ethernet.patch \
    file://mxl5007t-add-no_probe-and-no_reset-parameters.patch \
    file://nfs-max-rwsize-8k.patch \
    file://rt2800usb_fix_warn_tx_status_timeout_to_dbg.patch \
    file://tda18271-advertise-supported-delsys.patch \
    file://vlan_depth.patch \
    "

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/tmp"

FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_install_append() {
    ${STRIP} ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm ${D}${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
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
