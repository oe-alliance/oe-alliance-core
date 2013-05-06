DESCRIPTION = "A default skin for Enigma2."
MAINTAINER = "OE-Allinace"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README.md;md5=ec650e1109116ae1feccebec8d4e01e1"

inherit autotools gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r16"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-e2-skindefault.git;protocol=git"

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

