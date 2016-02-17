DESCRIPTION = "The concurrent.futures module provides a high-level interface for asynchronously executing callables."
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=241bf7fe9279183aa379ca9fbb567c36"
HOMEPAGE = "https://pypi.python.org/pypi/futures/3.0.3"
DEPENDS = "python"

SRC_URI = "https://pypi.python.org/packages/source/f/futures/futures-3.0.3.tar.gz"
SRC_URI[md5sum] = "32171f72af7e80c266310794adc4db46"
SRC_URI[sha256sum] = "2fe2342bb4fe8b8e217f0d21b5921cbe5408bf966d9f92025e707e881b198bed"

S = "${WORKDIR}/futures-${PV}"

inherit setuptools

include python-package-split.inc