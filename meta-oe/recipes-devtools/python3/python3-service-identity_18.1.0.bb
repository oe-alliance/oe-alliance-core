SUMMARY = "Service identity verification for pyOpenSSL & cryptography."
DESCRIPTION = "service_identity aspires to give you all the tools you need for verifying whether a certificate is valid for the intended purposes."
HOMEPAGE = "https://service-identity.readthedocs.io/en/stable"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8a0f079f4e6a215d6bd6f9d97cab4d5f"

RDEPENDS_${PN} = "${PYTHON_PN}-attrs ${PYTHON_PN}-idna ${PYTHON_PN}-pyasn1 ${PYTHON_PN}-pyasn1-modules ${PYTHON_PN}-pyopenssl"

PYPI_PACKAGE = "service_identity"

inherit pypi setuptools3

SRC_URI[md5sum] = "c6b8bac93e7d899a1da313a19cc6570a"
SRC_URI[sha256sum] = "0858a54aabc5b459d1aafa8a518ed2081a285087f349fe3e55197989232e2e2d"

include ${PYTHON_PN}-package-split.inc
