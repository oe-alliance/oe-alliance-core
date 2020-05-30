SUMMARY = "Asynchronous Component based Event Application Framework"
HOMEPAGE = "http://circuitsframework.com/"
AUTHOR = "James Mills <prologic@shortcircuit.net.au>"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b209f6edbb40680bdf62b70a7c097101"

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

SRC_URI = "https://files.pythonhosted.org/packages/c4/53/0577ec33811bb4657722629f27c53c16439c149e466414f7f7a2789742e7/circuits-${PV}.tar.gz"
SRC_URI[md5sum] = "26daaca7f386c781820d2a1174d91325"
SRC_URI[sha256sum] = "b2fad9be2235f87480c194efc91eaeed3cb030c465d4697f1ca326ce3619bf9b"

S = "${WORKDIR}/circuits-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
