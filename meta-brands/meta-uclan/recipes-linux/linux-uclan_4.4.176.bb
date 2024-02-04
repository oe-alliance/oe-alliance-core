DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.4.176"
SRCDATE = "20210806"
SRCDATE:ustym4ks2ottx = "20221203"

COMPATIBLE_MACHINE = "^(ustym4kottpremium|ustym4ks2ottx)$"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR:append = "0"

KVTYPE = "mv200"
KVTYPE:ustym4ks2ottx = "mv300"

SRC_URI[mv200.md5sum] = "02e665fecda20f617001e56afca7d391"
SRC_URI[mv200.sha256sum] = "2a9ab0dc82d9fe4e0a8a551a6323b177be11f2679bc43233c0344ecf153e0b54"
SRC_URI[mv300.md5sum] = "9c400b45c9bc7949c97ddb5bf6714b1e"
SRC_URI[mv300.sha256sum] = "e5604bb3576ead02b23861b0dde082a2b219fe7a622d973f7a52aaafbc56f7bb"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "https://source.mynonpublic.com/uclan/uclan-linux-${PV}-${SRCDATE}.tar.gz;name=${KVTYPE} \
    file://defconfig \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    file://findkerneldevice.sh \
    file://fix-multiple-defs-yyloc.patch \
    file://fix-build-with-binutils-2.41.patch \
    file://cfg80211_Add_option_to_report_the_bss_entry_in_connect_result.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} /${KERNEL_IMAGEDEST}/findkerneldevice.sh"

KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

kernel_do_configure:prepend() {
	install -d ${B}/usr
	install -m 0644 ${WORKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append() {
	install -d ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
	install -m 0755 ${WORKDIR}/findkerneldevice.sh ${D}${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            /${KERNEL_IMAGEDEST}/./findkerneldevice.sh
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    true
}

do_rm_work() {
}