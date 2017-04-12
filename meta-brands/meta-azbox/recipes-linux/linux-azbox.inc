SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR_append = ".4"

KERNEL_IMAGE_MAXSIZE_azboxhd = "6815744"
#KERNEL_IMAGE_MAXSIZE_azboxme = "6815744"
#KERNEL_IMAGE_MAXSIZE_azboxminime = "6815744"

DEPENDS = "genromfs-native virtual/${TARGET_PREFIX}gcc"

inherit kernel machine_kernel_pr

PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"
ALLOW_EMPTY_kernel-dev = "1"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGETYPE = "zbimage-linux-xload"
KERNEL_IMAGEDEST = "tmp"

S = "${WORKDIR}/linux-${KV}"
B = "${WORKDIR}/build"

CFLAGS_prepend = "-I${WORKDIR} "
EXTRA_OEMAKE =+ " CONFIG_INITRAMFS_SOURCE=${STAGING_KERNEL_DIR}/initramfs"

do_configure_prepend() {
    sed -i "s#:= usr/initramfs_default_node_list#:= \$(srctree)/usr/initramfs_default_node_list#" ${STAGING_KERNEL_DIR}/usr/Makefile
    sed -i "s#\$(srctree)/arch/mips/boot/#\$(obj)/#" ${STAGING_KERNEL_DIR}/arch/mips/boot/Makefile
}

kernel_do_compile_prepend() {
    gcc ${CFLAGS} ${WORKDIR}/genzbf.c -o ${WORKDIR}/genzbf
    install -d ${B}/arch/${ARCH}/boot/
    install -m 0755 ${WORKDIR}/genzbf ${B}/arch/${ARCH}/boot/
}

kernel_do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
    oe_runmake ${KERNEL_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
}

kernel_do_compile_append() {
    rm -rf ${B}/arch/${ARCH}/boot/genzbf
    rm -rf ${B}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE}
    install -m 0644 ${WORKDIR}/zbimage-linux-xload ${B}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE}
}

# This is part of kernel.bbclass but doesn't get executed when not copied here
do_sizecheck() {
        if [ ! -z "${KERNEL_IMAGE_MAXSIZE}" ]; then
                cd ${B}
                size=`ls -lL ${KERNEL_OUTPUT} | awk '{ print $5}'`
                if [ $size -ge ${KERNEL_IMAGE_MAXSIZE} ]; then
                        die "This kernel (size=$size > ${KERNEL_IMAGE_MAXSIZE}) is too big for your device. Please reduce the size of the kernel by making more of it modular."
                fi
        fi
}
do_sizecheck[dirs] = "${B}"

addtask sizecheck before do_install after do_strip
