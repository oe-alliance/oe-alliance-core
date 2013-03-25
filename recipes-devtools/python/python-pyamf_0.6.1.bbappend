PRINC = "2"

inherit setuptools openpli-distutils

do_install() {
	distutils_do_install_keep_pyo
}

PACKAGES =+ " ${PN}-src ${PN}-tests"
RDEPENDS_{PN}-src = "${PN}"
FILES_${PN}-tests = " \
	${libdir}/${PYTHON_DIR}/site-packages/*/tests \
	"
FILES_${PN}-src = " \
	${libdir}/${PYTHON_DIR}/site-packages/*/*.py \
	${libdir}/${PYTHON_DIR}/site-packages/*/*/*.py \
	${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*.py \
	${libdir}/${PYTHON_DIR}/site-packages/*/*/*/*/*.py \
	"
