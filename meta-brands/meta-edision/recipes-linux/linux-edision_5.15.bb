SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
PACKAGE_ARCH = "${MACHINE_ARCH}"

KERNEL_RELEASE = "5.15"

COMPATIBLE_MACHINE = "^(osmini4k|osmio4k|osmio4kplus)$"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "c0d2fc41c606b3bc3d13ef4551cdf593"
SRC_URI[sha256sum] = "db0b1e2786161ebdd9e237e9d0376b08858b90d294ed9bf8ba32cf1f2d742037"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-brcmstb-${PV}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "flex-native bison-native openssl-native coreutils-native"

MACHINE_KERNEL_PR:append = "0"

SRC_URI += "https://source.mynonpublic.com/edision/linux-edision-${PV}.tar.gz \
    file://defconfig \
    file://findkerneldevice.py \
    file://0001-ata-ahci-fix-enum-constants-for-gcc-13.patch \
    "

S = "${WORKDIR}/linux-brcmstb-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}* /${KERNEL_IMAGEDEST}/findkerneldevice.py"

kernel_do_install:append () {
    install -d ${D}/${KERNEL_IMAGEDEST}
    install -m 0644 ${KERNEL_OUTPUT_DIR}/${KERNEL_IMAGETYPE} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    install -m 0644 ${UNPACKDIR}/findkerneldevice.py ${D}/${KERNEL_IMAGEDEST}
}

do_compile_kernelmodules:append() {
    # openembedded-core 0fc66a0b64953aae38d0124b57615fffaec8de52
    if (grep -q -i -e '^CONFIG_MODULES=y$' ${B}/.config); then
        # 5.10+ kernels have module.lds that we need to copy for external module builds
        if [ -e "${B}/scripts/module.lds" ]; then
            install -Dm 0644 ${B}/scripts/module.lds ${STAGING_KERNEL_BUILDDIR}/scripts/module.lds
        fi
    fi
}

pkg_postinst:kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ] ; then
            if grep -q 'root=/dev/mmcblk' /proc/cmdline ; then
                ${PYTHON_PN} /${KERNEL_IMAGEDEST}/findkerneldevice.py
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/kernel
            else
                flash_erase /dev/${MTD_KERNEL} 0 0
                nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
                rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
            fi
        fi
    fi
    true
}

pkg_postrm:kernel-image () {
}

do_rm_work() {
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
