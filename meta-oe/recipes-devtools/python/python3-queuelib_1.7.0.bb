SUMMARY  = "library that implements object collections which are stored in memory or persisted to disk, provide a simple API, and run fast"
HOMEPAGE = "https://github.com/scrapy/queuelib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a9dbf92a1904187a362d3fe098d4a1f"

SRC_URI[md5sum] = "14c25b6f39478e5483a2f0ae42fcfeb1"
SRC_URI[sha256sum] = "2855162096cf0230510890b354379ea1c0ff19d105d3147d349d2433bb222b08"

S = "${WORKDIR}/queuelib-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
