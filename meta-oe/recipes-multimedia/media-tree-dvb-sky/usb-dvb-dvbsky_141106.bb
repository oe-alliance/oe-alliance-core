DEPENDS = "virtual/kernel module-init-tools"
RDEPENDS_${PN} += "kmod"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

SRC_URI[md5sum] = "8073a7921a6f1e154083d71bc2ef5b46"
SRC_URI[sha256sum] = "b0a32dc6efb5cb62c0572938de10cd3d718d94191fe4648a9722b7fdddcad2d5"

SRC_URI = "http://www.dvbsky.net/download/linux/media_build-bst-14-141106.tar.gz \
           file://fix-strip.patch;patch=1 \
           file://defconfig \
"

SRC_URI_append_mipsel = "file://sit2_op.o "

SRC_URI_append_arm = " file://vu_backport.patch \
                       file://sit2_op.o_150322_arm \
"

PR = "r1"

S = "${WORKDIR}/media_build-bst-14"

EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR} SRCDIR=${STAGING_KERNEL_DIR} OUTDIR=${STAGING_KERNEL_BUILDDIR}"

do_populate_sysroot[noexec] = "1"

do_configure_prepend_mipsel() {
    CUR=`pwd`
    cp ${WORKDIR}/sit2_op.o ${S}/v4l/sit2_op.o
    make DIR=${STAGING_KERNEL_BUILDDIR} allyesconfig
    cd $CUR
}

do_configure_prepend_arm() {
    CUR=`pwd`
    cp ${WORKDIR}/sit2_op.o_150322_arm ${S}/v4l/sit2_op.o
    make DIR=${STAGING_KERNEL_BUILDDIR} allyesconfig
    cd $CUR
}

require usb-dvb-dvbsky.inc
