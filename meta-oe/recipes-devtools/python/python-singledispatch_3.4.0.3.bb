DESCRIPTION = "functools.singledispatch from Python 3.4 to Python 2.6"
HOMEPAGE = "https://pypi.python.org/pypi/singledistpatch"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=ee3cd67264adc7eb07981f3644dc17dc"

SRCNAME = "singledispatch"

SRC_URI = "https://pypi.python.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "af2fc6a3d6cc5a02d0bf54d909785fcb"
SRC_URI[sha256sum] = "5b06af87df13818d14f08a028e42f566640aef80805c3b50c5056b086e3c2b9c"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

include python-package-split.inc