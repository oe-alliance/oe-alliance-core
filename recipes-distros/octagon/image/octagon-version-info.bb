DESCRIPTION = "octagon version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "octagon"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r0-${DATETIME}-${DISTRO_TYPE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://www.octagon-forum.com"

S = "${WORKDIR}"

inherit autotools

PACKAGES = "${PN}"

do_install() {
			if [ "${DISTRO_TYPE}" = "experimental" ] ; then
				BUILDTYPE="1"
			else
				BUILDTYPE="0"
			fi
			# generate /etc/image-version
			install -d ${D}/etc
			echo "box_type=${MACHINE}" > ${D}/etc/image-version
			echo "build_type=${BUILDTYPE}" >> ${D}/etc/image-version
			echo "version=${IMAGE_VERSION}" >> ${D}/etc/image-version
			echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
			if [ "${MACHINE}" = "vusolo" -o "${MACHINE}" = "vuduo" -o "${MACHINE}" = "vuuno" -o "${MACHINE}" = "vuultimo" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/vuplus/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "et4x00" -o "${MACHINE}" = "et5x00" -o "${MACHINE}" = "et6x00" -o "${MACHINE}" = "et9x00" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/etxx00/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "odinm9" -o "${MACHINE}" = "odinm7" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "iqonios100hd" -o "${MACHINE}" = "iqonios200hd" -o "${MACHINE}" = "iqonios300hd" -o "${MACHINE}" = "tmtwin" -o "${MACHINE}" = "tm2t" -o "${MACHINE}" = "tmsingle" -o "${MACHINE}" = "tmnano" -o "${MACHINE}" = "tmnano2t"]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/iqon/iqon-dvb-modules.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "gb800solo" -o "${MACHINE}" = "gb800se" -o "${MACHINE}" = "gb800ue" -o "${MACHINE}" = "gbquad" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/gigablue/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "ventonhdx" -o "${MACHINE}" = "mbtwin" -o "${MACHINE}" = "inihde" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/ini/ini-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "xp1000" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/xp/xp-dvb-modules-${MACHINE}.bb | cut -b 12-19`	
			elif [ "${MACHINE}" = "dm8000" -o "${MACHINE}" = "dm7020hd" -o "${MACHINE}" = "dm500hd" -o "${MACHINE}" = "dm800se" ]; then
				DRIVERS="20120711"
			elif [ "${MACHINE}" = "dm800" ]; then
				DRIVERS="20120518"	
			else
				DRIVERS='N/A'
			fi
			echo "drivers=${DRIVERS}" >> ${D}/etc/image-version
			echo "date=${DATETIME}" >> ${D}/etc/image-version
			echo "comment=octagon" >> ${D}/etc/image-version
			echo "target=9" >> ${D}/etc/image-version
			echo "creator=octagon" >> ${D}/etc/image-version
			echo "url=${URL}" >> ${D}/etc/image-version
			echo "catalog=${URL}" >> ${D}/etc/image-version
			echo "${MACHINE}" > ${D}/etc/model
}

FILES_${PN} += "/etc"

