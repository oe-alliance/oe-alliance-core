SUMMARY  = "library that implements object collections which are stored in memory or persisted to disk, provide a simple API, and run fast"
HOMEPAGE = "https://github.com/scrapy/queuelib"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94fe8a350dbc22aca301c15a4d213b48"

DEPENDS += "python3-hatch-vcs-native"

SRC_URI[md5sum] = "bf79a57ad71acb6c107faa2115a43795"
SRC_URI[sha256sum] = "b12fea79fd8c1dd23e212b1f3db58003b773949801d4f4e6f34d882467d4a192"

inherit pypi python_hatchling

include python3-package-split.inc
