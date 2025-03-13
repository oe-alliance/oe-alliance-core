SUMMARY = "Service identity verification for pyOpenSSL & cryptography."
DESCRIPTION = "service_identity aspires to give you all the tools you need for verifying whether a certificate is valid for the intended purposes."
HOMEPAGE = "https://service-identity.readthedocs.io/en/stable"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=76edce6a3fa1b82b0bf2b6ce174c19e2"

DEPENDS += " \
    python3-hatch-vcs-native python3-hatch-fancy-pypi-readme-native \
"

RDEPENDS:${PN} = "python3-attrs python3-idna python3-pyasn1 python3-pyasn1-modules python3-pyopenssl"

PYPI_PACKAGE = "service_identity"

inherit pypi python_hatchling

SRC_URI[md5sum] = "e575db51719742ec39191c896e4c2971"
SRC_URI[sha256sum] = "b8683ba13f0d39c6cd5d625d2c5f65421d6d707b013b375c355751557cbe8e09"

include python3-package-split.inc
