SUMMARY = "Service identity verification for pyOpenSSL"
DESCRIPTION = "service_identity aspires to give you all the tools you need for verifying whether a certificate is valid for the intended purposes."
HOMEPAGE = "https://github.com/pyca/service_identity"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a0f079f4e6a215d6bd6f9d97cab4d5f"

PYPI_PACKAGE = "service_identity"
inherit pypi setuptools

SRC_URI[md5sum] = "f509cb41ca2f8420bd8496291136d6cc"
SRC_URI[sha256sum] = "4001fbb3da19e0df22c47a06d29681a398473af4aa9d745eca525b3b2c2302ab"

RDEPENDS_${PN} = "python-attrs python-idna python-pyasn1 python-pyasn1-modules python-pyopenssl"

include python-package-split.inc
