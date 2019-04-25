SUMMARY = "General purpose components extending the circuits framework."
HOMEPAGE = "https://pypi.org/project/circuits-bricks"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=0df7cbc8103621715eea843b81039b77"

SRC_URI = "https://files.pythonhosted.org/packages/source/c/circuits-bricks/circuits-bricks-${PV}.tar.gz"

S = "${WORKDIR}/circuits-bricks-${PV}"

inherit setuptools

SRC_URI[md5sum] = "8bcf94ba15fd2340ab6025fa80f3a8e6"
SRC_URI[sha256sum] = "334a1a9439e53557ac2b8b6e6e2151c99059404148e07b4360a8a43be0228b2b"

include python-package-split.inc
