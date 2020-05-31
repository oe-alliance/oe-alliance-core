SUMMARY = "Module for manipulating ID3 (v1 + v2) tags in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "${PYTHON_PN} ${PYTHON_PN}-setuptools-scm-native"
RDEPENDS_${PN} = "${PYTHON_PN}-shell"

SRC_URI = "https://files.pythonhosted.org/packages/96/9f/280220926cabbf4822f80e094a5190fb3df245209648e169c8bcf708697b/mutagen-1.44.0.tar.gz"
SRC_URI[md5sum] = "ce773321eedcb38b31d0624540191401"
SRC_URI[sha256sum] = "56065d8a9ca0bc64610a4d0f37e2bd4453381dde3226b8835ee656faa3287be4"

S = "${WORKDIR}/mutagen-${PV}"

inherit distutils3

include ${PYTHON_PN}-package-split.inc
