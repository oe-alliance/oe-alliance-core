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

SRC_URI[md5sum] = "7edc65f079f24f8b11e28f5464bced13"
SRC_URI[sha256sum] = "dfbd565384fc3fffeba121f5a3a2d0899ac1f756d41432ca0879933fbfb3401d"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
