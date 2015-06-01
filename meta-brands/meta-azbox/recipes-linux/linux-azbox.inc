SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS = "genromfs-native gcc"
DEPENDS_azboxhd = "genromfs-native azbox-hd-buildimage gcc"
DEPENDS_azboxminime = "genromfs-native azbox-minime-packer gcc"

inherit kernel machine_kernel_pr

KERNEL_IMAGE_MAXSIZE_azboxhd = "6815744"
#KERNEL_IMAGE_MAXSIZE_azboxme = "6815744"
#KERNEL_IMAGE_MAXSIZE_azboxminime = "6815744"

PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION}"

S = "${WORKDIR}/linux-${KV}"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "zbimage-linux-xload"
KERNEL_IMAGETYPE = "zbimage-linux-xload"
KERNEL_IMAGEDEST = "/tmp"
FILES_kernel-image = "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}"
ALLOW_EMPTY_kernel-dev = "1"

CFLAGS_prepend = "-I${WORKDIR} "

do_configure_prepend() {
    oe_machinstall -m 0644 ${WORKDIR}/defconfig ${S}/.config
    oe_runmake oldconfig
}

kernel_do_compile_prepend() {
    gcc ${CFLAGS} ${WORKDIR}/genzbf.c -o ${WORKDIR}/genzbf
    install -d ${S}/arch/${ARCH}/boot/
    install -m 0755 ${WORKDIR}/genzbf ${S}/arch/${ARCH}/boot/
}

kernel_do_compile() {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
    oe_runmake include/linux/version.h CC="${KERNEL_CC}" LD="${KERNEL_LD}"
    oe_runmake ${KERNEL_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${AR}" OBJDUMP="${OBJDUMP}" NM="${NM}"
    oe_runmake modules CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${AR}" OBJDUMP="${OBJDUMP}"
}

kernel_do_compile_append() {
    rm -rf ${S}/arch/${ARCH}/boot/genzbf

    # Replace freshly compiled kernel with pre-compiled one
    rm -rf ${S}/${KERNEL_IMAGETYPE}
    rm -rf ${S}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE}
    install -m 0644 ${WORKDIR}/zbimage-linux-xload ${S}/${KERNEL_IMAGETYPE}
    install -m 0644 ${WORKDIR}/zbimage-linux-xload ${S}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE}
}

#### Bitchbake workarounds ####

# Simulate shipping the trash we do not want but Bitbake won't let us skip

PACKAGES =+ "kernel-trash"
FILES_kernel-trash = "/boot/System.map* \
    /boot/Module.symvers* \
    /boot/config* \
    /boot/vmlinux* \
    "

# ... then forcibly eradicate the crap package
do_packagedata_append() {
    sed -i 's/ kernel-dev//g' ${WORKDIR}/pkgdata/linux-azbox
    sed -i 's/ kernel-trash//g' ${WORKDIR}/pkgdata/linux-azbox
    sed -i 's/ kernel-vmlinux//g' ${WORKDIR}/pkgdata/linux-azbox
    rm -f ${WORKDIR}/pkgdata/runtime/kernel-trash*
    rm -f ${WORKDIR}/pkgdata/runtime/kernel-vmlin*
    rm -f ${WORKDIR}/pkgdata/runtime-reverse/kernel-trash*
    rm -f ${WORKDIR}/pkgdata/runtime-reverse/kernel-vmlin*
    rm -rf ${WORKDIR}/packages-split/kernel-trash
    rm -rf ${WORKDIR}/packages-split/kernel-vmlinux
    if [ -e ${WORKDIR}/pkgdata/shlibs/kernel-trash.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-trash/usr
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-trash.list
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-trash.ver
    fi
    if [ -e ${WORKDIR}/pkgdata/shlibs2/kernel-trash.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-trash/usr
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-trash.list
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-trash.ver
    fi
    if [ -e ${WORKDIR}/pkgdata/shlibs/kernel-dev.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-dev/usr
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-dev.list
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-dev.ver
    fi
    if [ -e ${WORKDIR}/pkgdata/shlibs2/kernel-dev.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-dev/usr
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-dev.list
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-dev.ver
    fi
    if [ -e ${WORKDIR}/pkgdata/shlibs/kernel-vmlinux.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-vmlinux
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-vmlinux.list
        rm -rf ${WORKDIR}/pkgdata/shlibs/kernel-vmlinux.ver
    fi
    if [ -e ${WORKDIR}/pkgdata/shlibs2/kernel-vmlinux.list ]; then
        rm -rf ${WORKDIR}/packages-split/kernel-vmlinux
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-vmlinux.list
        rm -rf ${WORKDIR}/pkgdata/shlibs2/kernel-vmlinux.ver
    fi
}

# Whatever we set for KERNEL_IMAGETYPE, bitchbake will ignore and happily
# append a version number, fix that:
do_install_append() {
    mv ${D}/tmp/zbimage-linux-xload-${KV}-opensat ${D}/tmp/zbimage-linux-xload
}

### Checks
## Stop bitbake from being a smart-ass ... well ... just ass ...
# Our initramfs has libs - surprise surprise ...
INSANE_SKIP_${$PN} = "already-stripped libdir"
INSANE_SKIP_${$PN}-base = "already-stripped libdir"
INSANE_SKIP_${$PN}-image = "already-stripped libdir"
INSANE_SKIP_${$PN}-dbg = "already-stripped libdir"
INSANE_SKIP_${$PN}-dev = "already-stripped libdir"

# ... that won't keep bitchbake from ... well ... bitching ...
# ... we have to skip package_qa
do_package_qa() {
}

## Do some useful check instead
# A too large kernel can brick the box
do_sizecheck() {
        if [ ! -z "${KERNEL_IMAGE_MAXSIZE}" ]; then
                cd ${S}
                size=`ls -lL ${KERNEL_OUTPUT} | awk '{ print $5}'`
                if [ $size -ge ${KERNEL_IMAGE_MAXSIZE} ]; then
                        die "This kernel (size=$size > ${KERNEL_IMAGE_MAXSIZE}) is too big for your device. Please reduce the size of the kernel by making more of it modular."
                fi
        fi
}
do_sizecheck[dirs] = "${S}"

addtask sizecheck before do_install after do_strip

