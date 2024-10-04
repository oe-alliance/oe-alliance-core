SUMMARY  = "itemloaders is a library that helps you collect data from HTML and XML sources."
HOMEPAGE = "https://github.com/scrapy/itemloaders"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

SRC_URI[md5sum] = "07e11fabfc7d85ff84d6e22abcb26eb5"
SRC_URI[sha256sum] = "4faf5b3abe83bf014476e3fd9ccf66867282971d9f1d4e96d9a61b60c3786770"

S = "${WORKDIR}/itemloaders-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
