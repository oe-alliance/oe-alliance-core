MAINTAINER = "OpenVisionE2 team"
SUMMARY:${PN} = "NCam ${PKGV}"
DESCRIPTION:${PN} = "NCam Open Source Softcam\n \
- latest git revision"

#SRC_URI="git://github.com/oe-special/NCam.git;protocol=https;branch=master"
#SRC_URI="git://github.com/OpenVisionE2/NCam.git;protocol=https;branch=master"
SRC_URI="git://github.com/fairbird/NCam.git;protocol=https;branch=master"

CAMNAME = "ncam"

EXTRA_OECMAKE += "\
	-DHAVE_PCSC=0 \
"

require ncam-common.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"

PV = "git"
PKGV = "git${GITPKGV}"
PR = "r0"

S = "${WORKDIR}/git"
