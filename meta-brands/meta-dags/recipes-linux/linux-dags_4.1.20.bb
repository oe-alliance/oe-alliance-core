DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPL-2.0-only"
SECTION = "kernel"

KV = "4.1.20"
DATETIME = "20180321"

MACHINE_KERNEL_PR = "r2"

COMPATIBLE_MACHINE = "dags72604"

RPROVIDES:${KERNEL_PACKAGE_NAME}-image += "kernel-${KERNEL_IMAGETYPE}"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[md5sum] = "710b7af46d7ac1c78e3ef683c5c0a6ad"
SRC_URI[sha256sum] = "1bb6b4f0d559885b3bd5f18c66a50a8ff39a284a81ad4da16188d08b9461ec55"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "https://source.mynonpublic.com/dags/linux-${KV}-${DATETIME}.tar.xz \
    file://defconfig \
    ${KERNEL_PATCH_GCC_SERIES} \
    ${KERNEL_PATCH_BINUTILS241_V2} \
    ${KERNEL_PATCH_WIFI_EXTAUTH_41} \
"

S = "${UNPACKDIR}/linux"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES:${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/zImage"

kernel_do_install:append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" EXTRA_CFLAGS=-Wno-attribute-alias
}

pkg_postinst:kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p1
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm:kernel-image () {
}

do_rm_work() {
}
