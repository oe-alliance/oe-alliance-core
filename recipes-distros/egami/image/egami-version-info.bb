DESCRIPTION = "EGAMI version info"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "EGAMI Support team"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
					file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}-${DISTRO_TYPE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://www.egami-image.com"

S = "${WORKDIR}"

inherit autotools

PACKAGES = "${PN}"

do_install() {
			if [ "${DISTRO_TYPE}" = "experimental" ] ; then
				BUILDTYPE="1"
			else
				BUILDTYPE="0"
			fi

			install -d ${D}/etc
			# generate /etc/image-version
			echo "box_type=${MACHINE}" > ${D}/etc/image-version
			echo "build_type=${BUILDTYPE}" >> ${D}/etc/image-version
			echo "version=${IMAGE_VERSION}" >> ${D}/etc/image-version
			echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
			echo "date=${DATETIME}" >> ${D}/etc/image-version
			echo "comment=EGAMI" >> ${D}/etc/image-version
			echo "target=9" >> ${D}/etc/image-version
			echo "creator=EGAMI" >> ${D}/etc/image-version
			echo "url=${URL}" >> ${D}/etc/image-version
			echo "catalog=${URL}" >> ${D}/etc/image-version
}

FILES_${PN} += "/etc"

