SUMMARY = "Fuzzy string matching in python"
HOMEPAGE = "https://github.com/seatgeek/fuzzywuzzy"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9eea716ad75a54962b0802f278da3ccb"

inherit pypi setuptools3

SRC_URI[md5sum] = "29708593c35b1ca67c329f853d9abcd0"
SRC_URI[sha256sum] = "45016e92264780e58972dca1b3d939ac864b78437422beecebb3095f8efd00e8"

include ${PYTHON_PN}-package-split.inc
