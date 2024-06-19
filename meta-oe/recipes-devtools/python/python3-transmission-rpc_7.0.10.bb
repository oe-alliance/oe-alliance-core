SUMMARY = "Python module that implements the Transmission bittorent client JSON-RPC protocol"
HOMEPAGE = "https://github.com/trim21/transmission-rpc"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0fbf1900d3022358b905196180f6b937"

RDEPENDS:${PN} += "python3-requests \
                   python3-typing-extensions \
"

PYPI_PACKAGE = "transmission_rpc"

inherit pypi python_setuptools_build_meta

SRC_URI[md5sum] = "2094a0f1e4d7830d0b879db809aeae33"
SRC_URI[sha256sum] = "175c389f70f40790b788744abb40b36d6e2187d4623d85f42b75b574deb65830"

include ${PYTHON_PN}-package-split.inc
