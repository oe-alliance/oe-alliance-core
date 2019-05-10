SUMMARY = "Fuzzy string matching in python"
HOMEPAGE = "https://github.com/seatgeek/fuzzywuzzy"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9eea716ad75a54962b0802f278da3ccb"

inherit pypi setuptools

SRC_URI[md5sum] = "b6d756069739fd5198f9d4c013272535"
SRC_URI[sha256sum] = "6f49de47db00e1c71d40ad16da42284ac357936fa9b66bea1df63fed07122d62"

include python-package-split.inc
