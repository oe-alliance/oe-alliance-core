SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel machine_kernel_pr kernel-fixups

KERNEL_RELEASE = "4.4.8"
SRCDATE = "20160504"

COMPATIBLE_MACHINE = "^(et7x00|et8500)$"

MACHINE_KERNEL_PR = "r2"

SRC_URI[md5sum] = "8b67a95176111385270ed3276436193a"
SRC_URI[sha256sum] = "571438a1c94f5a72903b310682a344757cbf97028e1e5dfbe980472f04a4e9a2"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

SRC_URI += "https://source.mynonpublic.com/xtrend/xtrend-linux-${PV}-${SRCDATE}.tar.xz \
    file://defconfig \
    file://add-dmx-source-timecode.patch \
    file://iosched-slice_idle-1.patch \
    file://0001-Support-TBS-USB-drivers-for-4.3-kernel.patch \
    file://0001-TBS-fixes-for-4.3-kernel.patch \
    ${KERNEL_PATCHES_DVB_STV_SERIES} \
    ${KERNEL_PATCH_FIX_NEVER_NULL} \
    ${KERNEL_PATCH_BINUTILS241_V3} \
    ${KERNEL_PATCH_FIX_LOG2} \
    ${KERNEL_PATCH_WIFI_CFG80211} \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

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
