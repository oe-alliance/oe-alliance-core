SUMMARY = "URL normalization for Python"
HOMEPAGE = "https://github.com/niksite/url-normalize"
AUTHOR = "Nikolay Panov <github@npanov.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0355248f9f4025eb234b21ac43b9ad7a"

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/ec/ea/780a38c99fef750897158c0afb83b979def3b379aaac28b31538d24c4e8f/url-normalize-1.4.3.tar.gz"
SRC_URI[md5sum] = "3e72f94de0c4a98f8ea76cd99cf105d9"
SRC_URI[sha256sum] = "d23d3a070ac52a67b83a1c59a0e68f8608d1cd538783b401bc9de2c0fac999b2"

S = "${WORKDIR}/url-normalize-1.4.3"

RDEPENDS:${PN} = "${PYTHON_PN}-six"

include ${PYTHON_PN}-package-split.inc
