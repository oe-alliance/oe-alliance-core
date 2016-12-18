DESCRIPTION = "Amlogic Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI[md5sum] = "c13b491e28dccd10dc65f32db7743fd0"
SRC_URI[sha256sum] = "e4f4c4c82d86abe23fce5d1b212d93d597e50dfaa4249d4792203a989de9c9c4"

inherit kernel machine_kernel_pr
MACHINE_KERNEL_PR_append = ".0"

DEPENDS = "xz-native bc-native u-boot-mkimage-native gcc"

LINUX_VERSION ?= "3.14.29"
LINUX_VERSION_EXTENSION ?= "amlogic"

COMPATIBLE_MACHINE = "(wetekplay2)"


SRC_URI = "http://source.mynonpublic.com/wetek/linux-amlogic-amlogic-3.14.y.tar.gz \
   file://kernel-add-support-for-gcc6.patch \
   file://defconfig \
   file://boot.ini \
   file://uInitrd \
"

S = "${WORKDIR}/linux-amlogic-amlogic-3.14.y"
B = "${WORKDIR}/build"

do_compile_prepend () {
    if test -n "${KERNEL_DEVICETREE}"; then
        for DTB in ${KERNEL_DEVICETREE}; do
            if echo ${DTB} | grep -q '/dts/'; then
                bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
                DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
            fi
            oe_runmake ${DTB}
        done
    # Create directory, this is needed for out of tree builds
    mkdir -p ${B}/arch/arm64/boot/dts/amlogic/
    fi
}
do_compile_append() {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0644 ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${DEPLOY_DIR_IMAGE}/meson64_wetekplay2.dtb
    install -m 0644 ${WORKDIR}/boot.ini ${DEPLOY_DIR_IMAGE}/boot.ini
    install -m 0644 ${WORKDIR}/uInitrd ${DEPLOY_DIR_IMAGE}/uInitrd
}

do_rm_work() {
}
do_package_qa() {
}
