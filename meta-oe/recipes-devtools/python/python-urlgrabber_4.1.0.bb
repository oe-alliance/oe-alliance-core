DESCRIPTION = "urlgrabber is a pure python package that drastically simplifies the fetching of files."
HOMEPAGE = "http://urlgrabber.baseurl.org"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68ad62c64cc6c620126241fd429e68fe"

DEPENDS = "${PYTHON_PN}-pycurl"

SRC_URI = "https://files.pythonhosted.org/packages/b1/23/61cb4d829138f24bfae2c77af6794ddd67240811dbb4e3e2eb22c4f57742/urlgrabber-${PV}.tar.gz \
        file://urlgrabber-reset.patch"
SRC_URI[md5sum] = "dc9bf17b007c42814d4a511d47699de7"
SRC_URI[sha256sum] = "075af8afabae6362482d254e5ac3ffa595d1766117b684e53d9c25c2e937e139"

S = "${WORKDIR}/urlgrabber-${PV}"

inherit distutils

include ${PYTHON_PN}-package-split.inc
