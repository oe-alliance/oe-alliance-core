SUMMARY = "PNG Assistant"
LICENSE = "proprietary"
require conf/license/license-gplv2.inc

DEPENDS = "swig-native libpng ${PYTHON_PN}"

SRC_URI = "\
    file://png_util.cpp \
    file://png_util.h \
    file://png_util.i \
"

S = "${WORKDIR}/${P}"
UNPACKDIR = "${S}"

inherit setuptools3-base ${PYTHON_PN}-dir

do_compile() {
    swig -python -c++ ${UNPACKDIR}/png_util.i
    ${CXX} -O2 -c -fPIC ${UNPACKDIR}/png_util.cpp ${UNPACKDIR}/png_util_wrap.cxx -I${STAGING_INCDIR}/${PYTHON_DIR}
    ${CXX} -shared ${UNPACKDIR}/png_util.o ${UNPACKDIR}/png_util_wrap.o -o _png_util.so -L${STAGING_LIBDIR} -lpng -fPIC
    mv ${UNPACKDIR}/png_util.py ${UNPACKDIR}
}

do_install() {
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/pngutil
    touch ${D}/${PYTHON_SITEPACKAGES_DIR}/pngutil/__init__.py
    install -m 0755 ${UNPACKDIR}/png_util.py ${D}/${PYTHON_SITEPACKAGES_DIR}/pngutil
    install -m 0755 ${UNPACKDIR}/_png_util.so ${D}/${PYTHON_SITEPACKAGES_DIR}/pngutil
}

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}/pngutil"
