DESCRIPTION = "ViX version info"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "ViX team"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
					file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL_vuuno = "http://www.vuplus-support.co.uk"
URL_vuultimo = "http://www.vuplus-support.co.uk"
URL_vusolo = "http://www.vuplus-support.co.uk"
URL_vuduo = "http://www.vuplus-support.co.uk"
URL_et4x00 = "http://www.xtrend-support.co.uk"
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
			elif [ "${MACHINE}" = "odinm9" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/odin/odin-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "tmtwin" -o "${MACHINE}" = "tm2t" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/technomate/technomate-dvb-modules.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "gb800solo" -o "${MACHINE}" = "gb800se" -o "${MACHINE}" = "gb800ue" -o "${MACHINE}" = "gbquad" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/gigablue/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
			elif [ "${MACHINE}" = "ventonhdx" -o "${MACHINE}" = "ventonhde" ]; then
				DRIVERS=`grep "SRCDATE = " ${OE-ALLIANCE_BASE}/meta-oe-alliance/recipes-bsp/venton/venton-dvb-modules-hdx.bb | cut -b 12-19`
			else
				DRIVERS='N/A'
			fi
			echo "drivers=${DRIVERS}" >> ${D}/etc/image-version
			echo "date=${DATETIME}" >> ${D}/etc/image-version
			echo "comment=ViX" >> ${D}/etc/image-version
			echo "target=9" >> ${D}/etc/image-version
			echo "creator=openViX" >> ${D}/etc/image-version
			echo "url=${URL}" >> ${D}/etc/image-version
			echo "catalog=${URL}" >> ${D}/etc/image-version
}

FILES_${PN} = "/etc/image-version /etc/oe-git.log /etc/e2-git.log"

