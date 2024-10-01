SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

MODULE = "linux-4.1.20"

COMPATIBLE_MACHINE = "^(gb7252|gb72604)$"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "r11"

SRC_DATE = "20180206"
SRC_DATE:gbquad4kpro = "20240729"

SRC_NAME = "legacy"
SRC_NAME:gbquad4kpro = "pro"

SRC_URI[legacy.md5sum] = "6036c5d722071e72d5d66dbf7ee74992"
SRC_URI[legacy.sha256sum] = "eff7eecf55dd75ecb44bd8b8fe16f588d19c1eac92125eaed2b6834348d12def"
SRC_URI[pro.md5sum] = "7280e29239f962717fcf5db4b73ef3be"
SRC_URI[pro.sha256sum] = "3f90048b6fbf3335959ed4a9ed07a24f6ea6545f11643c7fb7c720a8ca5fd5c7"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/gigablue/linux/gigablue-linux-${PV}-${SRC_DATE}.tar.gz;name=${SRC_NAME} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'initrd', 'file://defconfig_initrd' , 'file://defconfig', d)} \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://gbfindkerneldevice.py \
    file://0002-linux_dvb-core.patch \
    file://0002-bcmgenet-recovery-fix.patch \
    file://0002-linux_4_1_1_9_dvbs2x.patch \
    file://0002-linux_dvb_adapter.patch \
    file://0002-linux_rpmb_not_alloc.patch \
    file://kernel-add-support-for-gcc6.patch \
    file://0001-regmap-add-regmap_write_bits.patch \
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
    file://0018-Add-support-for-dvb-usb-stick-Hauppauge-WinTV-dualHD.patch \
    file://0001-dvb-usb-fix-a867.patch \
    file://0001-Support-TBS-USB-drivers-for-4.1-kernel.patch \
    file://0001-TBS-fixes-for-4.1-kernel.patch \
    file://0001-STV-Add-PLS-support.patch \
    file://0001-STV-Add-SNR-Signal-report-parameters.patch \
    file://blindscan2.patch \
    file://0001-stv090x-optimized-TS-sync-control.patch \
    file://kernel-add-support-for-gcc7.patch \
    file://kernel-add-support-for-gcc8.patch \
    file://kernel-add-support-for-gcc9.patch \
    file://kernel-add-support-for-gcc10.patch \
    file://kernel-add-support-for-gcc11.patch \
    file://kernel-add-support-for-gcc12.patch \
    file://kernel-add-support-for-gcc13.patch \
    file://kernel-add-support-for-gcc14.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    file://0003-uaccess-dont-mark-register-as-const.patch \
    file://add-partition-specific-uevent-callbacks-for-partition-info.patch \
    file://move-default-dialect-to-SMB3.patch \
    file://fix-multiple-defs-yyloc.patch \
    file://linux3.4-ARM-8933-1-replace-Sun-Solaris-style-flag-on-section.patch \
    file://fix-build-with-binutils-2.41.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage /${KERNEL_IMAGEDEST}/gbfindkerneldevice.py"

kernel_do_configure:prepend() {
        install -d ${B}/usr
        install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
        if [ -e ${UNPACKDIR}/defconfig_initrd ]; then
            mv ${UNPACKDIR}/defconfig_initrd ${UNPACKDIR}/defconfig
        fi
}
kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${UNPACKDIR}/gbfindkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" EXTRA_CFLAGS="-Wno-attribute-alias"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            ${PYTHON_PN} /${KERNEL_IMAGEDEST}/gbfindkerneldevice.py
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
    true
}

pkg_postrm:kernel-image () {
}

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-gigablue-${KV}:"

do_rm_work() {
}
