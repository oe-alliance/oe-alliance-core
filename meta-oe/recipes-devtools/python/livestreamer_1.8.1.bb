DESCRIPTION = "Livestreamer is CLI program that extracts streams \
	from various services and pipes them into a video player of choice."
HOMEPAGE = "http://livestreamer.tanuki.se/en/latest/"
SECTION = "devel/python"
LICENSE = "BSD"
PACKAGE_ARCH = "all"
require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "python \
	python-ctypes \
	python-misc \
	python-pkgutil \
	python-requests \
	python-shell \
	python-subprocess \
	"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.8.1+git${SRCPV}"
PKGV = "1.8.1+git${GITPKGV}"
VER ="1.8.1"
PR = "r0"

SRC_URI = "git://github.com/chrippa/livestreamer.git;protocol=git"

S = "${WORKDIR}/git/"

inherit setuptools

do_install_append() {
   rm -rf ${D}${bindir}
   rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/livestreamer_cli
}

FILES_${PN} = " \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*.pyo \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*/*.pyo \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*/*/*.pyo \
	"

PACKAGES =+ "${PN}-src"

FILES_${PN}-src = " \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer-*.egg-info/* \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*.py \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*/*.py \
	${libdir}/${PYTHON_DIR}/site-packages/livestreamer/*/*/*.py \
	"

