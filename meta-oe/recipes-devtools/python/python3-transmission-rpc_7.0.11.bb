SUMMARY = "Python module that implements the Transmission bittorent client JSON-RPC protocol"
HOMEPAGE = "https://github.com/trim21/transmission-rpc"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fbf1900d3022358b905196180f6b937"

RDEPENDS:${PN} += "python3-requests \
                   python3-typing-extensions \
"

PYPI_PACKAGE = "transmission_rpc"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "ee201e2554ed34e5b509e12ee6821031"
SRC_URI[sha256sum] = "5872322e60b42e368bc9c4724773aea4593113cb19bd2da589f0ffcdabe57963"

include ${PYTHON_PN}-package-split.inc
