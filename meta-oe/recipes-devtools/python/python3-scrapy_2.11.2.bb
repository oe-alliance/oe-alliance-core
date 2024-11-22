SUMMARY  = "Scrapy is a fast high-level web crawling and web scraping framework"
HOMEPAGE = "https://scrapy.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

RDEPENDS:${PN} = "python3-twisted \
    python3-cryptography \
    python3-cssselect \
    python3-pyopenssl \
    python3-zopeinterface \
    python3-service-identity \
    python3-itemloaders \
    python3-parsel \
    python3-queuelib \
    python3-w3lib \
    python3-protego \
    python3-itemadapter \
    python3-h2 \
"

SRC_URI[md5sum] = "7edc65f079f24f8b11e28f5464bced13"
SRC_URI[sha256sum] = "dfbd565384fc3fffeba121f5a3a2d0899ac1f756d41432ca0879933fbfb3401d"

inherit pypi setuptools3_legacy

include python3-package-split.inc
