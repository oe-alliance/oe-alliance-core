SUMMARY = "Python SNMP Toolkit"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=d4aed8ba2a0ff36c85e2753b66c36b45"

inherit pypi python_poetry_core

SRC_URI[md5sum] = "33203585898e346a35bd49c2344deb5b"
SRC_URI[sha256sum] = "37ac595c7f0c1c00514505939b4dcf5b4fd5a9ffe51b0349f60bb640c11b0f77"

include python3-package-split.inc
