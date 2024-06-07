DEPENDS = "virtual/kernel module-init-tools"
RDEPENDS:${PN} += "kmod"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

SRC_URI[md5sum] = "66990bd8b8aa3759d764552a7ccac013"
SRC_URI[sha256sum] = "b3c612d792834d14c981c400022ec923c154e10161121cf730a09b4ed4e35b04"


SRC_URI = "http://dvbsky.net/download/linux/media_build-bst-160430.tar.gz \
           file://vu_kernel_4.1.20.patch \
           file://defconfig \
           file://sit2_op.o_150322_arm \
"

PR = "r0"

S = "${WORKDIR}/media_build-bst-160430"

EXTRA_OEMAKE = "KDIR=${STAGING_KERNEL_DIR} SRCDIR=${STAGING_KERNEL_DIR} OUTDIR=${STAGING_KERNEL_BUILDDIR}"

do_populate_sysroot[noexec] = "1"

do_configure:prepend:arm() {
    CUR=`pwd`
    cp ${UNPACKDIR}/sit2_op.o_150322_arm ${S}/v4l/sit2_op.o
    make DIR=${STAGING_KERNEL_BUILDDIR} allyesconfig
    cd $CUR
}

FILESEXTRAPATHS:prepend := "${THISDIR}/usb-dvb-dvbsky_160430:"

require usb-dvb-dvbsky.inc
