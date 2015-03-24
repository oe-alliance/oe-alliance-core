SUMMARY = "cryptography is a package designed to expose cryptographic recipes and primitives to Python developers."
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8c3617db4fb6fae01f1d253ab91511e4"

DEPENDS_${PN} = "python-cffi-native python-pycparser-native python-six python-enum34 python-pyasn1"
RDEPENDS_${PN} = "python-six python-cffi python-pycparser"

PR = "r1"

SRC_URI = "http://pypi.python.org/packages/source/c/cryptography/cryptography-${PV}.tar.gz"

SRC_URI[md5sum] = "70dde78a5515abdbfd7a3d58f15689ab"
SRC_URI[sha256sum] = "f4e041bc83c1be94d87116a7aa201c378b7c6581be4d83994b2da0a84499f73b"

S = "${WORKDIR}/cryptography-${PV}"

inherit setuptools

include python-package-split.inc
