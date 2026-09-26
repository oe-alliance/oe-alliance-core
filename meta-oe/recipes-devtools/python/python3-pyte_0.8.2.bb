SUMMARY = "Simple VTXXX-compatible terminal emulator."
HOMEPAGE = "https://github.com/selectel/pyte"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS += "python3-pytest-runner-native"

inherit pypi setuptools3

SRC_URI[sha256sum] = "5af970e843fa96a97149d64e170c984721f20e52227a2f57f0a54207f08f083f"

RDEPENDS:${PN} = "python3-wcwidth"

include python3-package-split.inc
