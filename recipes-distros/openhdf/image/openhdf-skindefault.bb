DESCRIPTION = "A default skin for Enigma2."
MAINTAINER = "OpenHDF"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README.md;md5=eb9d15edf13c844790932eefa0069e2c"

inherit autotools gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r11"

SRC_URI = "git://github.com/openhdf/skin_default.git;protocol=git"

S = "${WORKDIR}/git"

PV_font-roboto-enigma = "2013.02.21"
PR_font-roboto-enigma = "r0"
PKGV_font-roboto-enigma = "${PV_font-roboto-enigma}"
DESCRIPTION_font-roboto-enigma = "Roboto enigma font"

PACKAGES =+ " font-roboto-enigma"
PROVIDES =+ " font-roboto-enigma"
FILES_font-roboto-enigma = "${datadir}/fonts"
FILES_${PN} = "${datadir}/enigma2"

RDEPENDS_${PN} = "font-roboto-enigma"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

