DESCRIPTION = "OpenViX Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ViX team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = " \
	oe-alliance-enigma2 \
	openvix-enigma2 \
	openvix-bootlogo \
	avahi-daemon \
	dropbear \
	early-configure \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	fakelocale \
	libavahi-client \
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

export IMAGE_BASENAME = "openxix-image"
IMAGE_LINGUAS = ""

inherit image

URL_vuuno = "http://www.vuplus-support.co.uk"
URL_vuultimo = "http://www.vuplus-support.co.uk"
URL_vusolo = "http://www.vuplus-support.co.uk"
URL_vuduo = "http://www.vuplus-support.co.uk"
URL_et5x00 = "http://www.xtrend-support.co.uk"
URL_et6x00 = "http://www.xtrend-support.co.uk"
URL_et9x00 = "http://www.xtrend-support.co.uk"
URL_tmtwin = "http://www.technomate-support.co.uk"
URL_odinm9 = "http://www.odin-support.co.uk"
URL_gb800solo = "http://www.gigablue-support.co.uk"
URL_gb800se = "http://www.gigablue-support.co.uk"
URL_gb800ue = "http://www.gigablue-support.co.uk"
URL_gbquad = "http://www.gigablue-support.co.uk"
URL_ventonhdx = "http://www.venton-support.co.uk"

rootfs_postprocess() {
			curdir=$PWD
			cd ${IMAGE_ROOTFS}

			# because we're so used to it
			ln -s opkg usr/bin/ipkg || true
			ln -s opkg-cl usr/bin/ipkg-cl || true

			if [ "${DISTRO_TYPE}" = "experimental" ] ; then
				BUILDTYPE="1"
			else
				BUILDTYPE="0"
			fi
			# generate /etc/image-version
			echo "box_type=${MACHINE}" > etc/image-version
			echo "build_type=${BUILDTYPE}" >> etc/image-version
			echo "version=${IMAGE_VERSION}" >> etc/image-version
			echo "build=${BUILD_VERSION}" >> etc/image-version
			if [ "${MACHINE}" = "vusolo" -o "${MACHINE}" = "vuduo" -o "${MACHINE}" = "vuuno" -o "${MACHINE}" = "vuultimo" ]; then
				DRIVERS=`grep SRCDATE_vusolo ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/vuplus/vuplus-dvb-modules-${MACHINE}.bb | cut -b 19-26`
			elif [ "${MACHINE}" = "et5x00" -o "${MACHINE}" = "et6x00" -o "${MACHINE}" = "et9x00" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/etxx00/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "odinm9" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "tmtwin" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/technomate/tm-dvb-modules.bb | cut -b 12-21`
			elif [ "${MACHINE}" = "gb800solo" -o "${MACHINE}" = "gb800se" -o "${MACHINE}" = "gb800ue" -o "${MACHINE}" = "gbquad" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/gigablue/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "ventonhdx" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/venton/venton-dvb-modules-hdx.bb | cut -b 12-19`
			else
				DRIVERS='N/A'
			fi
			echo "drivers=${DRIVERS}" >> etc/image-version
			echo "date=${DATETIME}" >> etc/image-version
			echo "comment=ViX" >> etc/image-version
			echo "target=9" >> etc/image-version
			echo "creator=openViX" >> etc/image-version
			echo "url=${URL}" >> etc/image-version
			echo "catalog=${URL}" >> etc/image-version

			install -m 0644 ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-openvix/image/files/releasenotes etc/releasenotes

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
			echo "Issuer: Team VIX" >> ${NFO}
			echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
			if [ "${DESC}" != "" ]; then
					echo "Description: ${DESC}" >> ${NFO}
					echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
			fi
}

do_rootfs_append() {
			generate_nfo
}
