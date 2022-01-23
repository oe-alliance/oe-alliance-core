SUMMARY  = "itemloaders is a library that helps you collect data from HTML and XML sources."
HOMEPAGE = "https://github.com/scrapy/itemloaders"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

SRC_URI[md5sum] = "3bd9752494deb721d320d9beaea89c8e"
SRC_URI[sha256sum] = "1277cd8ca3e4c02dcdfbc1bcae9134ad89acfa6041bd15b4561c6290203a0c96"

S = "${WORKDIR}/itemloaders-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
