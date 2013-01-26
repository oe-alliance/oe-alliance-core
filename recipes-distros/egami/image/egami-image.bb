DESCRIPTION = "EGAMI Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "EGAMI team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	egami-enigma2 \
	egami-bootlogo \
	egami-version-info \
	${ENIGMA2_PLUGINS} \
	${ENIGMA2_INI_PLUGINS} \
	${ENIGMA2_USB_DRV} \
	avahi-daemon \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	e2fsprogs-blkid \
	fakelocale \
	libavahi-client \
	libcrypto-compat \
	ntpdate \
	opkg \
	sdparm \
	task-base \
	task-core-boot \
	tzdata \
	util-linux-sfdisk \
	vsftpd \
	ntfs-3g \
	hddtemp \
	busybox-cron \
	python-gdata \
	ushare \
	task-base-smbfs \
	task-base-smbfs-client \
	mc \
	egami-base-files \
	"

ENIGMA2_PLUGINS = "\
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-foreca \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-addstreamurl \
	enigma2-plugin-extensions-zaphistorybrowser \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-systemplugins-3gmodemmanager \
	enigma2-plugin-systemplugins-positionersetup \
"

ENIGMA2_INI_PLUGINS = "\
	enigma2-plugin-picons-default-ventonsupport \
	enigma2-plugin-extensions-inimytube \
	enigma2-plugin-extensions-egamipermanentclock \
	enigma2-plugin-extensions-egamiboot \
"

ENIGMA2_USB_DRV = "\
	enigma2-plugin-drivers-dvb-usb-af9035 \
	enigma2-plugin-drivers-dvb-usb-dib0700 \
	enigma2-plugin-drivers-dvb-usb-af9015 \
	enigma2-plugin-drivers-dvb-usb-siano \
	enigma2-plugin-drivers-dvb-usb-dw2102 \
	enigma2-plugin-drivers-dvb-usb-as102 \
	enigma2-plugin-drivers-dvb-usb-dtt200u \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-drivers-dvb-usb-dib0700 \
	enigma2-plugin-drivers-dvb-usb-af9015 \
	enigma2-plugin-drivers-dvb-usb-siano \
	enigma2-plugin-drivers-dvb-usb-em28xx  \
	enigma2-plugin-drivers-dvb-usb-it913x \
	enigma2-plugin-drivers-dvb-usb-pctv452e \
"


export IMAGE_BASENAME = "egami-image"
IMAGE_LINGUAS = ""

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

generate_nfo() {
			VER=`grep Version: "${IMAGE_ROOTFS}/usr/lib/ipkg/info/enigma2.control" | cut -b 10-26`
			echo "Enigma2: ${VER}" > ${NFO}
			echo "Machine: ${MACHINE}" >> ${NFO}
			DATE=`date +%Y-%m-%d' '%H':'%M`
			echo "Date: ${DATE}" >> ${NFO}
			echo "Issuer: EGAMI" >> ${NFO}
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
