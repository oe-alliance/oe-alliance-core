SUMMARY = "This module implements the password-based key derivation function, PBKDF2, specified in RSA PKCS#5 v2.0."
HOMEPAGE = "https://www.dlitz.net/software/python-pbkdf2/"
AUTHOR = "Dwayne C. Litzenberger <dlitz@dlitz.net>"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.txt;md5=b6948f1f30b14743a80831eea2c7dfba"

SRC_URI = "https://files.pythonhosted.org/packages/02/c0/6a2376ae81beb82eda645a091684c0b0becb86b972def7849ea9066e3d5e/pbkdf2-${PV}.tar.gz"
SRC_URI[md5sum] = "40cda566f61420490206597243dd869f"
SRC_URI[sha256sum] = "ac6397369f128212c43064a2b4878038dab78dab41875364554aaf2a684e6979"

S = "${WORKDIR}/pbkdf2-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
