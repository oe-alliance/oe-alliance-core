SUMMARY = "This is a python library for accessing resources protected by OAuth 2.0"
HOMEPAGE = "http://github.com/google/oauth2client"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=038e1390e94fe637991fa5569daa62bc"

RDEPENDS_${PN} = "python-httplib2 python-six python-uritemplate"

SRC_URI = "https://files.pythonhosted.org/packages/a6/7b/17244b1083e8e604bf154cf9b716aecd6388acd656dd01893d0d244c94d9/oauth2client-${PV}.tar.gz"

SRC_URI[md5sum] = "3a9eb781f685949c04946f6c09e4c11d"
SRC_URI[sha256sum] = "d486741e451287f69568a4d26d70d9acd73a2bbfa275746c535b4209891cccc6"

S = "${WORKDIR}/oauth2client-${PV}"

inherit setuptools

include python-package-split.inc
