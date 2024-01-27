SUMMARY = "Service identity verification for pyOpenSSL & cryptography."
DESCRIPTION = "service_identity aspires to give you all the tools you need for verifying whether a certificate is valid for the intended purposes."
HOMEPAGE = "https://service-identity.readthedocs.io/en/stable"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=76edce6a3fa1b82b0bf2b6ce174c19e2"

DEPENDS += " \
    ${PYTHON_PN}-hatch-vcs-native ${PYTHON_PN}-hatch-fancy-pypi-readme-native \
"

RDEPENDS:${PN} = "${PYTHON_PN}-attrs ${PYTHON_PN}-idna ${PYTHON_PN}-pyasn1 ${PYTHON_PN}-pyasn1-modules ${PYTHON_PN}-pyopenssl"

PYPI_PACKAGE = "service_identity"

inherit pypi python_hatchling

SRC_URI[md5sum] = "64267a4351ee3c225ee12a11bb29fc44"
SRC_URI[sha256sum] = "6829c9d62fb832c2e1c435629b0a8c476e1929881f28bee4d20bc24161009221"

include ${PYTHON_PN}-package-split.inc
