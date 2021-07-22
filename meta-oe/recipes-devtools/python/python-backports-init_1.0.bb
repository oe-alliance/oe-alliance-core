SUMMARY = "Helper package to avoid backports/__init__.py conflicts"
DETAIL = "backports packages in python2 suffer from a flaw in the namespace \
implementation and can conflict with each other. For OE purposes, at least \
fix the conflicting install of .../site-packages/backports/__init__.py"
AUTHOR = "Tim Orling <ticotimo@gmail.com>"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://backports/__init__.py"

inherit python-dir

# provide to avoid warnings
do_compile() {
    :
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/backports
    install ${WORKDIR}/backports/__init__.py ${D}${PYTHON_SITEPACKAGES_DIR}/backports/
}

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/backports/__init__.py"

BBCLASSEXTEND = "native nativesdk"
