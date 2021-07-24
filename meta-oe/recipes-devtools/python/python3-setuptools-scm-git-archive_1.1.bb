SUMMARY = "setuptools_scm plugin for git archives"
HOMEPAGE = "https://github.com/Changaco/setuptools_scm_git_archive/"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=838c366f69b72c5df05c96dff79b35f2"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI = "https://files.pythonhosted.org/packages/7e/2c/0c15b29a1b5940250bfdc4a4f53272e35cd7cf8a34159291b6b4ec9eb291/setuptools_scm_git_archive-${PV}.tar.gz"
SRC_URI[md5sum] = "1c9351fa5cebd12e76488737a7c78f2e"
SRC_URI[sha256sum] = "6026f61089b73fa1b5ee737e95314f41cb512609b393530385ed281d0b46c062"

S = "${WORKDIR}/setuptools_scm_git_archive-${PV}"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc


BBCLASSEXTEND = "native nativesdk"
