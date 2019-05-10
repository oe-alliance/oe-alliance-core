SUMMARY = "Pexpect allows easy control of interactive console applications."
HOMEPAGE = "https://pexpect.readthedocs.io"
SECTION = "devel/python"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1c7a725251880af8c6a148181665385b"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-core \
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-terminal \
    ${PYTHON_PN}-resource \
    ${PYTHON_PN}-fcntl \
    ${PYTHON_PN}-ptyprocess \
"

SRC_URI = "https://files.pythonhosted.org/packages/1c/b1/362a0d4235496cb42c33d1d8732b5e2c607b0129ad5fdd76f5a583b9fcb3/pexpect-${PV}.tar.gz"

SRC_URI[md5sum] = "ed003242cbf308aee1b1eaecdef59e43"
SRC_URI[sha256sum] = "9e2c1fd0e6ee3a49b28f95d4b33bc389c89b20af6a1255906e90ff1262ce62eb"

inherit pypi setuptools

BBCLASSEXTEND = "native nativesdk"

include python-package-split.inc
