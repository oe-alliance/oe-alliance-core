SUMMARY  = "itemloaders is a library that helps you collect data from HTML and XML sources."
HOMEPAGE = "https://github.com/scrapy/itemloaders"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

SRC_URI[md5sum] = "0b8d7f1310813be5cdf9ce54a65b7830"
SRC_URI[sha256sum] = "fc2307f984116b010d6101a68a6a133ac8de927320b0ab696f31ad710a8d8d98"

S = "${WORKDIR}/itemloaders-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
