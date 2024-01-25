SUMMARY = "Context managers by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.context"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "b40aeeda6a07dd283f2a407f622cda5b"
SRC_URI[sha256sum] = "4dad2404540b936a20acedec53355bdaea223acb88fd329fa6de9261c941566e"

inherit pypi setuptools3


