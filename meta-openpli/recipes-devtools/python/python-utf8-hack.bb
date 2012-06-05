DESCRIPTION = "Sets Python's default encoding to UTF8"
SECTION = "devel/python"
DEPENDS = "python"
PRIORITY = "optional"
PV = "0.2"

require conf/license/openpli-gplv2.inc

inherit distutils-base

SRC_URI = "file://sitecustomize.py"
S = "${WORKDIR}"

PACKAGES="${PN}"
FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages/*.py*"

do_compile() {
	python -O -m compileall ${WORKDIR}
}

do_install() {
	install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/
	install -m 644 ${S}/sitecustomize.py ${D}${libdir}/${PYTHON_DIR}/site-packages/	
	install -m 644 ${S}/sitecustomize.pyo ${D}${libdir}/${PYTHON_DIR}/site-packages/	
}
