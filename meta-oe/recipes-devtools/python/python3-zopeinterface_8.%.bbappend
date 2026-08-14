# Upgrade to 8.2 for Python 3.14 compatibility (classdictcell / __annotate_func__)
PV = "8.2"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=471a6a1f304e3b652c42106dc6ed78a7"
PYPI_PACKAGE = "zope_interface"
SRC_URI:remove = "file://0001-Allow-using-setuptools-74.patch"
SRC_URI[sha256sum] = "afb20c371a601d261b4f6edb53c3c418c249db1a9717b0baafc9a9bb39ba1224"

DEPENDS += "python3-wheel-native"

include python3-package-split.inc
