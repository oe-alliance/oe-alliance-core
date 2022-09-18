SUMMARY = "CherryPy is a pythonic, object-oriented HTTP framework"
HOMEPAGE = "https://cheroot.cherrypy.org/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a5ad8f932e1fd3841133f20d3ffedda1"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"
RDEPENDS:${PN} = "${PYTHON_PN}-jaraco.collections ${PYTHON_PN}-jaraco.classes ${PYTHON_PN}-jaraco.text ${PYTHON_PN}-jaraco.functools ${PYTHON_PN}-zc.lockfile ${PYTHON_PN}-tempora"

SRC_URI = "https://files.pythonhosted.org/packages/60/ea/6c4d16b0cd1f4f64a478bac8a37d75a585e854afb5693ce80a9711efdc4a/CherryPy-${PV}.tar.gz"

SRC_URI[md5sum] = "033c58bf3da497f283b039911d1c882b"
SRC_URI[sha256sum] = "9b48cfba8a2f16d5b6419cc657e6d51db005ba35c5e3824e4728bb03bbc7ef9b"

S = "${WORKDIR}/CherryPy-${PV}"

inherit setuptools3_legacy

include ${PYTHON_PN}-package-split.inc
