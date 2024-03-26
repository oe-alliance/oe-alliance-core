MAINTAINER = "Raed - fairbird"
SUMMARY:${PN} = "NCam ${PKGV}"
DESCRIPTION:${PN} = "NCam Open Source Softcam\n \
- latest git revision"

SRC_URI="git://github.com/fairbird/NCam.git;protocol=https;branch=master"

CAMNAME = "ncam"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require ncam-common.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "1.20+git"
PKGV = "1.20+git${GITPKGV}"
PR = "r0"

S = "${WORKDIR}/git"
