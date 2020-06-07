MODULE = "EPGImportFilter"
DESCRIPTION = "EPGImport Filter"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "\
	${PYTHON_PN}-difflib \
	${PYTHON_PN}-shell \
	"

inherit ${@bb.utils.contains("PYTHON_PN", "python", "distutils-openplugins", "distutils3-openplugins", d)} gitpkgv
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

DEPENDS += "enigma2"

SRC_URI = "git://github.com/E2OpenPlugins/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

SRCREV = "${AUTOREV}"

