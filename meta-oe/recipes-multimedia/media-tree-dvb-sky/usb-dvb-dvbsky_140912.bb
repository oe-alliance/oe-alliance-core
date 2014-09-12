DEPENDS = "virtual/kernel module-init-tools"
RDEPENDS_${PN} += "kmod"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"
PR = "r4"

SRC_URI[md5sum] = "aa2823c3d88bf0b761b480c7c8e020fd"
SRC_URI[sha256sum] = "402ea698f63a88dcfa5baf161ad553c34a0454bd97f77ba49ac0db9065b9eab7"

SRC_URI = "http://www.dvbsky.net/download/linux/meida_build-bst-14-140912.tar.gz \
           file://fix-strip.patch;patch=1 \
           file://defconfig \
           file://sit2_op.o \
"

S = "${WORKDIR}/media_build-bst-14"

do_configure_prepend() {
    CUR=`pwd`
    cp ${WORKDIR}/sit2_op.o ${S}/v4l/sit2_op.o
    make DIR=${STAGING_KERNEL_DIR} allyesconfig
    cd $CUR
}

require usb-dvb-dvbsky.inc
