SUMMARY = "Python process list module"
SECTION = "devel/python"
PRIORITY = "optional"
SRCNAME = "process"

require conf/license/license-gplv2.inc

inherit distutils-base

PV = "1.0"
PKGV = "1.0"
PR = "r7"

SRC_URI = "file://process.py"

S = "${WORKDIR}"

PACKAGES = "${PN} ${PN}-src"

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/process.pyo"
FILES_${PN}-src = "${PYTHON_SITEPACKAGES_DIR}/process.py"
RDEPENDS_{PN}-src = "${PN}"

do_compile() {
    python -O -m compileall ${WORKDIR}
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    install -m 644 ${S}/process.py ${D}${PYTHON_SITEPACKAGES_DIR}/    
    install -m 644 ${S}/process.pyo ${D}${PYTHON_SITEPACKAGES_DIR}/    
}
