SUMMARY = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

KERNEL_RELEASE = "4.10.12"

COMPATIBLE_MACHINE = "^(7005s|7105s|7215s|7225s|8100s)$"

inherit kernel machine_kernel_pr kernel-fixups

SRC_URI[mips.md5sum] = "1d85dbb87cd57d6147213c65d73fed9e"
SRC_URI[mips.sha256sum] = "54bd9694d08c98991174818d85189691d87530a67871938595e889bd36ca0caa"
SRC_URI[arm.md5sum] = "bda1c09ed92a805cedc6770c0dd40e81"
SRC_URI[arm.sha256sum] = "67a3ac98727595a399d5c399d3b66a7fadbe8136ac517e08decba5ea6964674a"

LIC_FILES_CHKSUM = "file://${UNPACKDIR}/linux-${PV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

MACHINE_KERNEL_PR:append = "6"

SRC_URI += "https://source.mynonpublic.com/ceryon/ceryon-linux-${PV}-${ARCH}.tar.gz;name=${ARCH} \
    file://defconfig \
    file://export_pmpoweroffprepare.patch \
    ${KERNEL_PATCHES_SERIES_4_10} \
    ${KERNEL_PATCHES_DVB_SI2157} \
    ${KERNEL_PATCHES_DVB_SI2168} \
    ${KERNEL_PATCHES_DVB_MYGICA_V333} \
    ${KERNEL_PATCHES_DVB_MYGICA_V334} \
    ${KERNEL_PATCHES_DVB_MYGICA_V335} \
    file://0002-cp1emu-do-not-use-bools-for-arithmetic.patch \
    file://0005-xbox-one-tuner-4.10.patch \
    ${KERNEL_PATCH_WIFI_EXTAUTH_410} \
    "

SRC_URI:append:arm = " \
    file://findkerneldevice.sh \
    file://initramfs-subdirboot.cpio.gz;unpack=0 \
    ${KERNEL_PATCH_DVB_RESERVE} \
    file://blacklist_mmc0.patch \
    "

SRC_URI:append:mipsel = " \
    file://block2mtd.patch \
    file://initramfs-mipsel.cpio.xz;unpack=0 \
    "

S = "${UNPACKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

# Linux MIPS Models

KERNEL_OUTPUT:mipsel = "vmlinux"
KERNEL_IMAGETYPE:mipsel = "vmlinux"

KERNEL_EXTRA_ARGS = 'EXTRA_CFLAGS="-std=gnu17 -Wno-attribute-alias"'

RPROVIDES:${KERNEL_PACKAGE_NAME}-image:mipsel += "kernel-${KERNEL_IMAGETYPE}"

FILES:${KERNEL_PACKAGE_NAME}-image:mipsel = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}*"

kernel_do_configure:prepend:mipsel() {
	install -d ${B}/usr
	install -m 0644 ${UNPACKDIR}/initramfs-mipsel.cpio.xz ${B}/
}

kernel_do_install:append:mipsel () {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
	rm ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst:kernel-image:mipsel () {
	if [ "x$D" == "x" ]; then
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
			flash_erase /dev/${MTD_KERNEL} 0 0
			nandwrite -p /dev/${MTD_KERNEL} /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
			rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
		fi
	fi
	true
}

# Linux ARM Models

KERNEL_OUTPUT:arm = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_IMAGETYPE:arm = "zImage"

FILES:${KERNEL_PACKAGE_NAME}-image:arm = "/${KERNEL_IMAGEDEST}/findkerneldevice.sh"

kernel_do_configure:prepend:arm() {
    install -d ${B}/usr
    install -m 0644 ${UNPACKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install:append:arm() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${UNPACKDIR}/findkerneldevice.sh ${D}/${KERNEL_IMAGEDEST}
}

pkg_postinst:kernel-image:arm () {
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

# extra tasks
addtask kernel_link_images after do_compile before do_install
