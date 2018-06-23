SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"

MODULE = "linux-4.1.20"

inherit kernel machine_kernel_pr

MACHINE_KERNEL_PR_append = "r2"

SRC_URI[md5sum] = "6036c5d722071e72d5d66dbf7ee74992"
SRC_URI[sha256sum] = "eff7eecf55dd75ecb44bd8b8fe16f588d19c1eac92125eaed2b6834348d12def"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://source.mynonpublic.com/gigablue/linux/gigablue-linux-${PV}-20180206.tar.gz \
	file://defconfig \
	file://gbfindkerneldevice.py \
	file://linux_dvb-core.patch \
	file://bcmgenet-recovery-fix.patch \
	file://linux_4_1_1_9_dvbs2x.patch \
	file://kernel-add-support-for-gcc6.patch \
	file://0001-Support-TBS-USB-drivers-for-4.1-kernel.patch \
	file://0001-TBS-fixes-for-4.1-kernel.patch \
	file://0001-STV-Add-PLS-support.patch \
	file://0001-STV-Add-SNR-Signal-report-parameters.patch \
	file://blindscan2.patch \
	file://0001-stv090x-optimized-TS-sync-control.patch \
"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/zImage /${KERNEL_IMAGEDEST}/gbfindkerneldevice.py"

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${WORKDIR}/gbfindkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} ] ; then
            python /${KERNEL_IMAGEDEST}/gbfindkerneldevice.py
            dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
        fi
    fi
    rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
    true
}

pkg_postrm_kernel-image () {
}

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-gigablue-${KV}:"

do_rm_work() {
}
