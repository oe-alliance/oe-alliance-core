PRINC = "1"

inherit setuptools openpli-distutils

do_compile() {
}

do_install() {
	distutils_do_install_keep
}

do_install_append() {
	rm -fR ${D}${PYTHON_SITEPACKAGES_DIR}/zope/interface/test*
	rm -fR ${D}${PYTHON_SITEPACKAGES_DIR}/zope/interface/common/test*
}

PACKAGES =+ "${PN}-src"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-src += "${PYTHON_SITEPACKAGES_DIR}/zope/interface/*.py"
FILES_${PN}-src += "${PYTHON_SITEPACKAGES_DIR}/zope/interface/*/*.py"

