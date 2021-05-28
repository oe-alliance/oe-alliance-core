SUMMARY = "yEnc Module for Python3 modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=6a2c8ff804d3c6776b89bd2754b443d3"

SRC_URI = "https://files.pythonhosted.org/packages/b4/87/9a87e95f64ba4d992d25fae5af969edf4ed46b31c70767551326b1cdbeca/sabyenc3-4.0.1.tar.gz"
SRC_URI[md5sum] = "50ff582b092c985809f8cb31f898ef04"
SRC_URI[sha256sum] = "74249291e6c5623b8b881cb175990c95d928c9d8d0346aa1cf85018d079869fc"

S = "${WORKDIR}/sabyenc3-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
