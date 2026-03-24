SUMMARY  = "Scrapy is a fast high-level web crawling and web scraping framework"
HOMEPAGE = "https://scrapy.org"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=786239b0f3b0d9c9403f6eecf35820dd"

DEPENDS += "python3-hatch-vcs-native"

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
    python3-defusedxml \
    python3-lxml \
    python3-packaging \
    python3-tldextract \
"

SRC_URI[md5sum] = "193d87287c778d7924f534404056168c"
SRC_URI[sha256sum] = "23bf5d37503ad16973bd09604b9c36249bd4bbc282c48ddedbf58105d4639337"

inherit pypi python_hatchling

include python3-package-split.inc
