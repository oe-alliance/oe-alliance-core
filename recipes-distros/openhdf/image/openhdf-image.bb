DESCRIPTION = "OpenHDF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "HDFreaks Team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	openhdf-version-info \
	openhdf-enigma2 \
	openhdf-bootlogo \
	${ENIGMA2_PLUGINS} \
	avahi \
	avahi-daemon \
	busybox-cron \
	dosfstools \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	e2fsprogs-blkid \
	fakelocale \
	hddtemp \
	hdparm \
	libavahi-client \
	libcrypto-compat \
	ntfs-3g \
	ntp \
	openvpn \
	opkg \
	sdparm \
	task-base \
	task-core-boot \
	tzdata \
	util-linux-sfdisk \
	ushare \
	volatile-media \
	vsftpd \
	"

ENIGMA2_PLUGINS = " "
ENIGMA2_PLUGINS_append_gb800solo = ""
ENIGMA2_PLUGINS_append_gb800se = ""
ENIGMA2_PLUGINS_append_gb800ue = "python-imaging enigma2-plugin-extensions-etportal"
ENIGMA2_PLUGINS_append_gbquad = "python-imaging webbrowser-utils enigma2-plugin-extensions-webbrowser enigma2-plugin-extensions-etportal"
ENIGMA2_PLUGINS_append_tmtwin = "enigma2-plugin-systemplugins-osd3dsetup enigma2-plugin-extensions-etportal"

export IMAGE_BASENAME = "OpenHDF-Image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

rootfs_postprocess() {
			curdir=$PWD
			cd ${IMAGE_ROOTFS}

			# softcam startup at boot
			sed -i 's/: exit 0//g' etc/init.d/bootmisc.sh
			echo "if [ -e /etc/init.d/softcam ] ; then" >> etc/init.d/bootmisc.sh
			echo "        /etc/init.d/softcam restart " >> etc/init.d/bootmisc.sh
			echo "fi" >> etc/init.d/bootmisc.sh
			echo "if [ -e /etc/init.d/cardserver ] ; then" >> etc/init.d/bootmisc.sh
			echo "        /etc/init.d/cardserver restart " >> etc/init.d/bootmisc.sh
			echo ": exit 0" >> etc/init.d/bootmisc.sh
			echo "fi" >> etc/init.d/bootmisc.sh

			# because we're so used to it
			ln -s opkg usr/bin/ipkg || true
			ln -s opkg-cl usr/bin/ipkg-cl || true

			cd $curdir
			if [ -f ../../../meta-oe-alliance/recipes-distros/openhdf/custom/parser.sh ]; then
				cp ./../../../meta-oe-alliance/recipes-distros/openhdf/custom/parser.sh .
				./parser.sh
				rm -rf parser.sh
			fi
			

}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

generate_nfo() {
			VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
			echo "Enigma2: ${VER}" > ${NFO}
			echo "Machine: ${MACHINE}" >> ${NFO}
			DATE=`date +%Y-%m-%d' '%H':'%M`
			echo "Date: ${DATE}" >> ${NFO}
			echo "Issuer: oHDF Team" >> ${NFO}
			echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
			if [ "${DESC}" != "" ]; then
					echo "Description: ${DESC}" >> ${NFO}
					echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
			fi
			MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
			echo "MD5: ${MD5SUM}" >> ${NFO}
}

do_rootfs_append() {
			generate_nfo
}
