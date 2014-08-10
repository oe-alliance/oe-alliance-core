DEPENDS = "virtual/kernel module-init-tools"
RDEPENDS_${PN} += "kmod"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"
PR = "r3"

SRC_URI[md5sum] = "7726f5e5f745045dcccb760200cf125e"
SRC_URI[sha256sum] = "43710a04face91b37faaffc20744113c126996a2e84115994722f8155258771c"

SRC_URI = "http://www.dvbsky.net/download/linux/media_build-bst-14-140726.tar.gz \
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
