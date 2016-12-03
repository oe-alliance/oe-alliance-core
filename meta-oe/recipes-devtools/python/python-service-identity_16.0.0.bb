SUMMARY = "service_identity aspires to give you all the tools you need for verifying whether a certificate is valid for the intended purposes."
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a0f079f4e6a215d6bd6f9d97cab4d5f"

PR = "r0"

RDEPENDS_${PN} = "python-attr python-attrs python-pyasn1 python-pyasn1-modules python-pyopenssl"

SRC_URI = "https://pypi.python.org/packages/source/s/service_identity/service_identity-${PV}.tar.gz"

SRC_URI[md5sum] = "d52392597b9c44a740abf322bfdb21e6"
SRC_URI[sha256sum] = "0630e222f59f91f3db498be46b1d879ff220955d7bbad719a5cb9ad14e3c3036"

S = "${WORKDIR}/service_identity-${PV}"

inherit setuptools

export BUILD_SYS
export HOST_SYS

export STAGING_LIBDIR
export STAGING_INCDIR

include python-package-split.inc
