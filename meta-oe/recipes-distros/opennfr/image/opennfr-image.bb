DESCRIPTION = "OPENNFR Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OPENNFR team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
    opennfr-base \
    packagegroup-base-nfs \ 
    	"

export IMAGE_BASENAME = "opennfr-image"
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
			cd ${IMAGE_ROOTFS}/var/lib/opkg/lists
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-3rdparty
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-all
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-${MACHINE}
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-${MACHINE}_3rdparty
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-mips32el
			rm -rf ${IMAGE_ROOTFS}/var/lib/opkg/lists/oe-${MACHINEBUILD}
			cd $curdir
			
			cd ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web/client.pyo
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web/client.py
			mv ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web/client-neu.py ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web/client.py
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/site-packages/twisted/web/client-neu.py
			cd $curdir

			cd ${IMAGE_ROOTFS}/usr/lib/python2.7
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/argparse.pyo
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/argparse.py
			mv ${IMAGE_ROOTFS}/usr/lib/python2.7/argparse-neu.py ${IMAGE_ROOTFS}/usr/lib/python2.7/argparse.py
			rm -rf ${IMAGE_ROOTFS}/usr/lib/python2.7/argparse-neu.py
			cd $curdir

			#cd ${IMAGE_ROOTFS}/bin
			#	if [ "${TARGET_ARCH}" = "arm" ]; then
			#		rm -rf ${IMAGE_ROOTFS}/bin/sh
			#		ln -s ${IMAGE_ROOTFS}/bin/bash ${IMAGE_ROOTFS}/bin/sh || true
			#	fi
			#	if [ "${TARGET_ARCH}" = "mipsel" ]; then
			#		rm -rf ${IMAGE_ROOTFS}/bin/sh
			#		ln -s ${IMAGE_ROOTFS}/bin/bash ${IMAGE_ROOTFS}/bin/sh || true
			#	fi
			#cd $curdir

    set -x

    ipkgarchs="${ALL_MULTILIB_PACKAGE_ARCHS} ${SDK_PACKAGE_ARCHS}"
    unused="*-dbg_* *-dev_* *-staticdev_* *-doc_* *-demos_* *-examples_* *-sourcecode_* *-locale-* *-localedata-*"

    if [ ! -z "${DEPLOY_KEEP_PACKAGES}" ]; then
        return
    fi

    packagedirs="${DEPLOY_DIR_IPK}"
    for arch in $ipkgarchs; do
        packagedirs="$packagedirs ${DEPLOY_DIR_IPK}/$arch"
    done

    multilib_archs="${MULTILIB_ARCHS}"
    for arch in $multilib_archs; do
        packagedirs="$packagedirs ${DEPLOY_DIR_IPK}/$arch"
    done

    for pkgdir in $packagedirs; do
        if [ -e $pkgdir/ ]; then
            for i in ${unused}; do
                rm -f $pkgdir/$i;
            done;
        fi
    done			
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

do_generate_nfo() {
			VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
			echo "Enigma2: ${VER}" > ${NFO}
			echo "Machine: ${MACHINE}" >> ${NFO}
			DATE=`date +%Y-%m-%d' '%H':'%M`
			echo "Date: ${DATE}" >> ${NFO}
			echo "Issuer: OPENNFR" >> ${NFO}
			echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
			if [ "${DESC}" != "" ]; then
					echo "Description: ${DESC}" >> ${NFO}
					echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
			fi
			MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
			echo "MD5: ${MD5SUM}" >> ${NFO}
}


addtask generate_nfo after do_rootfs

