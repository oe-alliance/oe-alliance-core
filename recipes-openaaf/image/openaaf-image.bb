DESCRIPTION = "OpenAAF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openAAF team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	openaaf-version-info \
	openaaf-enigma2 \
	openaaf-bootlogo \
	${ENIGMA2_PLUGINS} \
	avahi-daemon \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	fakelocale \
	libavahi-client \
	libcrypto-compat \
	ntp \
	opkg \
	sdparm \
	task-base \
	task-core-boot \
	tzdata \
	util-linux-sfdisk \
	volatile-media \
	vsftpd \
	"

ENIGMA2_PLUGINS = "\
	enigma2-plugin-extensions-autotimer \
	enigma2-plugin-extensions-epgsearch \
	enigma2-plugin-extensions-imdb \
	enigma2-plugin-extensions-graphmultiepg \
	enigma2-plugin-extensions-mediaplayer \
	enigma2-plugin-extensions-cutlisteditor \
	enigma2-plugin-systemplugins-videotune \
	enigma2-plugin-extensions-cooltvguide \
	enigma2-plugin-extensions-enhancedmoviecenter \
	enigma2-plugin-settings-defaultsat \
	enigma2-plugin-systemplugins-vps \
	enigma2-plugin-extensions-bmediacenter \
	enigma2-plugin-extensions-dvdplayer \
	enigma2-plugin-systemplugins-autoresolution \
	enigma2-plugin-extensions-volume-adjust \
	enigma2-plugin-extensions-autobackup \
	enigma2-plugin-systemplugins-softwaremanager \
	enigma2-plugin-systemplugins-hotplug \
"

ENIGMA2_PLUGINS_append_et5x00 = "swap-workaround"
ENIGMA2_PLUGINS_append_vusolo = "swap-workaround"
ENIGMA2_PLUGINS_append_gb800se = "swap-workaround"
ENIGMA2_PLUGINS_append_gb800ue = "swap-workaround"
ENIGMA2_PLUGINS_append_gb800solo = "swap-workaround"
ENIGMA2_PLUGINS_append_dm800 = "swap-workaround"
ENIGMA2_PLUGINS_append_dm800se = "swap-workaround"
ENIGMA2_PLUGINS_append_dm500hd = "swap-workaround"



export IMAGE_BASENAME = "openaaf-image"
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
			echo "Issuer: openAAF" >> ${NFO}
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
