SUMMARY  = "RAR archive reader for Python"
HOMEPAGE = "https://github.com/markokr/rarfile"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2f31e224cbf0c29cb6c55f2bae0e165f"

SRC_URI[md5sum] = "e4ceaa894c05afdb4f9b0966b56fd410"
SRC_URI[sha256sum] = "67548769229c5bda0827c1663dce3f54644f9dbfba4ae86d4da2b2afd3e602a1"

S = "${WORKDIR}/rarfile-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
