SUMMARY  = "library that implements object collections which are stored in memory or persisted to disk, provide a simple API, and run fast"
HOMEPAGE = "https://github.com/scrapy/queuelib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "c64bb48ef41e464498755e3a94d5078d"
SRC_URI[sha256sum] = "4b207267f2642a8699a1f806045c56eb7ad1a85a10c0e249884580d139c2fcd2"

S = "${WORKDIR}/queuelib-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
