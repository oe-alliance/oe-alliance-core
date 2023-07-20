MODULE = "EPGImportFilter"
DESCRIPTION = "EPGImport Filter"

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "\
	${PYTHON_PN}-difflib \
	${PYTHON_PN}-shell \
	"

inherit setuptools3-openplugins gitpkgv
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

DEPENDS += "enigma2"

SRC_URI = "git://github.com/E2OpenPlugins/e2openplugin-${MODULE}.git;protocol=https;branch=master"

S="${WORKDIR}/git"

SRCREV = "${AUTOREV}"
