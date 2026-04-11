SUMMARY = "A minimal low-level HTTP client."
HOMEPAGE = "https://www.encode.io/httpcore/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=1c1f23b073da202e1f4f9e426490210c"

DEPENDS += "python3-setuptools-scm-native python3-hatchling-native python3-hatch-fancy-pypi-readme-native"

RDEPENDS:${PN} = "python3-h11 python3-certifi"

SRC_URI[md5sum] = "5157e7240632c2984cfda1f7ac54dfc7"
SRC_URI[sha256sum] = "6e34463af53fd2ab5d807f399a9b45ea31c3dfa2276f15a2c3f00afff6e176e8"

inherit pypi python_hatchling

include python3-package-split.inc
