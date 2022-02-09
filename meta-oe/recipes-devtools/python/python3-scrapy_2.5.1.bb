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

SRC_URI = "https://files.pythonhosted.org/packages/cc/f8/5f412e9cff97a330cb813b6771db8579944debe5e1f0f9dae93af46c2be1/Scrapy-2.5.1.tar.gz"

SRC_URI[md5sum] = "2c559a7dcd20fbd841796b1220f6c85b"
SRC_URI[sha256sum] = "13af6032476ab4256158220e530411290b3b934dd602bb6dacacbf6d16141f49"

S = "${WORKDIR}/Scrapy-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
