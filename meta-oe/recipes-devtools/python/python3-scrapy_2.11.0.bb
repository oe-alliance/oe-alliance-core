SUMMARY  = "Scrapy is a fast high-level web crawling and web scraping framework"
HOMEPAGE = "https://scrapy.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

RDEPENDS:${PN} = "${PYTHON_PN}-twisted \
    ${PYTHON_PN}-cryptography \
    ${PYTHON_PN}-cssselect \
    ${PYTHON_PN}-pyopenssl \
    ${PYTHON_PN}-zopeinterface \
    ${PYTHON_PN}-service-identity \
    ${PYTHON_PN}-itemloaders \
    ${PYTHON_PN}-parsel \
    ${PYTHON_PN}-queuelib \
    ${PYTHON_PN}-w3lib \
    ${PYTHON_PN}-protego \
    ${PYTHON_PN}-itemadapter \
    ${PYTHON_PN}-h2 \
"

PYPI_PACKAGE = "Scrapy"

SRC_URI[md5sum] = "acbbaf2f42cdd8cb503048e94e25e040"
SRC_URI[sha256sum] = "3cbdedce0c3f0e0482d61be2d7458683be7cd7cf14b0ee6adfbaddb80f5b36a5"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
