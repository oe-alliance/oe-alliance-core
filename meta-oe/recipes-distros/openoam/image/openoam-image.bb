SUMMARY = "OpenOAM Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OAM team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
    oe-alliance-enigma2 \
    openoam-version-info \
    opensif-enigma2 \
    opensif-bootlogo \
    avahi-daemon \
    dropbear \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-tune2fs \
    e2fsprogs-blkid \
    fakelocale \
    libavahi-client \
    ntp \
    opkg \
    sdparm \
    packagegroup-base \
    packagegroup-core-boot \
    tzdata \
    util-linux-sfdisk \
    volatile-media \
    vsftpd \
    nano \
    "

export IMAGE_BASENAME = "openoam-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

rootfs_postprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true

    cd $curdir
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

do_generate_nfo() {
    VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
    echo "Enigma2: ${VER}" > ${NFO}
    echo "Machine: ${MACHINE}" >> ${NFO}
    DATE=`date +%Y-%m-%d' '%H':'%M`
    echo "Date: ${DATE}" >> ${NFO}
    echo "Issuer: Team OAM" >> ${NFO}
    echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
    if [ "${DESC}" != "" ]; then
        echo "Description: ${DESC}" >> ${NFO}
        echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
    fi
    MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
    echo "MD5: ${MD5SUM}" >> ${NFO}
}

addtask generate_nfo after do_rootfs
