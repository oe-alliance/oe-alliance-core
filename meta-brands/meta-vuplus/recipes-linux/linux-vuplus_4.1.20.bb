SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"

MODULE = "linux-4.1.20"

MACHINE_KERNEL_PR_append = "oea4.4-r7"

inherit kernel machine_kernel_pr

KERNEL_SRC_VERSION = "1.9"

SRC_URI[md5sum] = "9403441e47266f37ce8d9e2cdf34159d"
SRC_URI[sha256sum] = "5f5a43e222716962336df55eb98bd96001de2caf7b7dce538e266f5ba6851af6"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://archive.vuplus.com/download/kernel/stblinux-4.1-${KERNEL_SRC_VERSION}.tar.bz2 \
    file://defconfig \
    file://linux_dvb-core.patch \
    file://bcmgenet-recovery-fix.patch \
    file://linux_4_1_1_9_dvbs2x.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://0001-regmap-add-regmap_write_bits.patch \
    file://0002-af9035-fix-device-order-in-ID-list.patch \
    file://0003-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-soloHD.patch \
    file://0004-af9035-add-USB-ID-07ca-0337-AVerMedia-HD-Volar-A867.patch \
    file://0005-Add-support-for-EVOLVEO-XtraTV-stick.patch \
    file://0006-dib8000-Add-support-for-Mygica-Geniatech-S2870.patch \
    file://0007-dib0700-add-USB-ID-for-another-STK8096-PVR-ref-desig.patch \
    file://0008-add-Hama-Hybrid-DVB-T-Stick-support.patch \
    file://0009-Add-Terratec-H7-Revision-4-to-DVBSky-driver.patch \
    file://0010-media-Added-support-for-the-TerraTec-T1-DVB-T-USB-tu.patch \
    file://0011-media-tda18250-support-for-new-silicon-tuner.patch \
    file://0012-media-dib0700-add-support-for-Xbox-One-Digital-TV-Tu.patch \
    file://0013-mn88472-Fix-possible-leak-in-mn88472_init.patch \
    file://0014-staging-media-Remove-unneeded-parentheses.patch \
    file://0015-staging-media-mn88472-simplify-NULL-tests.patch \
    file://0016-mn88472-fix-typo.patch \
    file://0017-mn88472-finalize-driver.patch \
    file://0001-dvb-usb-fix-a867.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://kernel-add-support-for-gcc9.patch \
    file://0001-Support-TBS-USB-drivers-for-4.1-kernel.patch \
    file://0001-TBS-fixes-for-4.1-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0003-uaccess-dont-mark-register-as-const.patch \
    file://move-default-dialect-to-SMB3.patch \
    "

SRC_URI_append = "${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", " file://linux_dvb_adapter.patch;patch=1;pnum=1", "", d)}"

S = "${WORKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" EXTRA_CFLAGS="-Wno-attribute-alias"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/${MTD_KERNEL}
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm_kernel-image () {
}

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-vuplus-${KV}:"

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
