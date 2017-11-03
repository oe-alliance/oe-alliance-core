SUMMARY = "A collection of ASN.1-based protocols modules"
DESCRIPTION = "A collection of ASN.1 modules expressed in form of pyasn1 classes. Includes protocols PDUs definition (SNMP, LDAP etc.) and various data structures (X.509, PKCS etc.)."
HOMEPAGE = "https://github.com/etingof/pyasn1-modules"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=280606d9c18f200e03e0c247ac61475a"

inherit pypi setuptools

SRC_URI[md5sum] = "c90b071d0dfe23dc25669c71e9b91c46"
SRC_URI[sha256sum] = "b07c17bdb34d6f64aafea6269f2e8fb306a57473f0f38d9a6ca389d6ab30ac4a"

RDEPENDS_${PN} = "python-pyasn1"

include python-package-split.inc
