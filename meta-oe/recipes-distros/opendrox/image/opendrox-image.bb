SUMMARY = "Opendrox Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Opendroid Team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "opendrox-base \
  ${@base_contains("MACHINE_FEATURES", "smallflash", "", \
  " \
  packagegroup-base-smbfs-client \
  packagegroup-base-smbfs \
  packagegroup-base-nfs \
  ", d)} \
  "

export IMAGE_BASENAME = "opendrox-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image


rootfs_postprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true
    ln -s usr/lib/enigma2/spinner usr/lib/enigma2/skin_default/spinner || true

    cd $curdir
    if [ -f ../../../meta-oe-alliance/recipes-distros/opendrox/custom/parser.sh ]; then
        cp ./../../../meta-oe-alliance/recipes-distros/opendrox/custom/parser.sh .
        ./parser.sh
        rm -rf parser.sh
    fi
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

do_generate_nfo() {
    VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
    echo "Enigma2: ${VER}" > ${NFO}
    echo "Machine: ${MACHINE}" >> ${NFO}
    DATE=`date +%Y-%m-%d' '%H':'%M`
    echo "Date: ${DATE}" >> ${NFO}
    echo "Issuer: opendrox" >> ${NFO}
    echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
    if [ "${DESC}" != "" ]; then
        echo "Description: ${DESC}" >> ${NFO}
        echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
    fi
    MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
    echo "MD5: ${MD5SUM}" >> ${NFO}
}

addtask generate_nfo after do_rootfs

