SUMMARY = "setuptools_scm plugin for git archives"
HOMEPAGE = "https://github.com/Changaco/setuptools_scm_git_archive/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=838c366f69b72c5df05c96dff79b35f2"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

PYPI_PACKAGE = "setuptools_scm_git_archive"

SRC_URI[md5sum] = "cfcdab6edbe6b710ab24ad355e7cf7a3"
SRC_URI[sha256sum] = "c418bc77b3974d3ac65f268f058f23e01dc5f991f2233128b0e16a69de227b09"

S = "${WORKDIR}/setuptools_scm_git_archive-${PV}"

inherit pypi setuptools3_legacy

include ${PYTHON_PN}-package-split.inc


BBCLASSEXTEND = "native nativesdk"
