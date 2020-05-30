SUMMARY = "A fast and full-featured template engine based on embedded Python"
HOMEPAGE = "http://www.kuwata-lab.com/tenjin/"
AUTHOR = "makoto kuwata <kwa@kuwata-lab.com>"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT-LICENSE;md5=22f394329e0a797afdde1365a5bafec5"

SRC_URI = "https://files.pythonhosted.org/packages/31/8f/53d4140a5100ce21fef6294ce06be82aa5b7942be27355e532343901eb57/Tenjin-${PV}.tar.gz"
SRC_URI[md5sum] = "61ee558b14ed9c845a9894ad9d56cbbf"
SRC_URI[sha256sum] = "a5e044f3e2c0fc5f5f1162273d541b8f592036c7f9ef99444ff540704e404697"

S = "${WORKDIR}/Tenjin-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
