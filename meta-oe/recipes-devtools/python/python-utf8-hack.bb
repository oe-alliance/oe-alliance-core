SUMMARY = "Sets Python's default encoding to UTF8"
SECTION = "devel/python"
DEPENDS = "python"
PRIORITY = "optional"
PV = "0.2"

require conf/license/license-gplv2.inc

inherit distutils-base

SRC_URI = "file://sitecustomize.py"
S = "${WORKDIR}"


PACKAGES = "${PN} ${PN}-src"

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/sitecustomize.pyo"
FILES_${PN}-src = "${PYTHON_SITEPACKAGES_DIR}/sitecustomize.py"
RDEPENDS_{PN}-src = "${PN}"

do_compile() {
    python -O -m compileall ${WORKDIR}/sitecustomize.py
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    install -m 644 ${S}/sitecustomize.py ${D}${PYTHON_SITEPACKAGES_DIR}/    
    install -m 644 ${S}/sitecustomize.pyo ${D}${PYTHON_SITEPACKAGES_DIR}/    
}
