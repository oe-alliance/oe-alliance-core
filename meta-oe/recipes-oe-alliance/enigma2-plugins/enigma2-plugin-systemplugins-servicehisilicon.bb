DESCRIPTION = "hisilicon service for enigma2"
AUTHOR = "zgemma-star"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "enigma2"
RDEPENDS_${PN} = "enigma2"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/zgemma-star/servicehisilicon.git;protocol=https;branch=master \
		file://0002-Allows-manual-selection-of-subtitles.patch \
		file://0001-tryfix-missing-Handling-eit-Subtitle.patch \
"
SRC_URI_openhdf = "git://github.com/zgemma-star/servicehisilicon.git;protocol=https;branch=openpli \
		file://0002-Allows-manual-selection-of-subtitles.patch \
		file://0001-tryfix-missing-Handling-eit-Subtitle.patch \
"
S = "${WORKDIR}/git"

inherit autotools gitpkgv pythonnative pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

EXTRA_OECONF = " \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

FILES_${PN} = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/*.pyo \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/servicehisilicon.so \
	"

FILES_${PN}-dev = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/*.py \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceHisilicon/servicehisilicon.la \
	"
