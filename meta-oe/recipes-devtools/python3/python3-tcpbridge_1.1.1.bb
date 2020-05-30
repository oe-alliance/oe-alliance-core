SUMMARY = "TCP bridge for data transfer"
DESCRIPTION = "Simple single and dual endpoint tcp bridges."
HOMEPAGE = "https://pypi.org/project/tcpbridge"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2434fd31b55d6326fc3dfe4246efcf96"

inherit pypi setuptools3

SRC_URI[md5sum] = "c256820012bf85c2e8814462b32c4e84"
SRC_URI[sha256sum] = "d970c3635a2f0115cccbc0b7c0f27f8b8cd6981ef8cbd5b95fc7b224a6313133"

include ${PYTHON_PN}-package-split.inc
