SUMMARY = "Backport of shutil.which from Python 3.3"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c2bbb540b5ece85753ba7e15f74e7bf8"

inherit pypi setuptools

PYPI_PACKAGE = "backports.shutil_which"

PACKAGES = "${PN}"

SRC_URI[md5sum] = "12184879a7c3c87c3ffb04bca313f113"
SRC_URI[sha256sum] = "dd439a7b02433e47968c25a45a76704201c4ef2167deb49830281c379b1a4a9b"

do_install_append() {
	# python-lzma already provides __init__.py(o) files
	rm -rf ${D}${libdir}/${PYTHON_DIR}/site-packages/backports/__init__.py*
}
