SUMMARY = "Context managers by jaraco"
HOMEPAGE = "https://github.com/jaraco/jaraco.context"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7a7126e068206290f3fe9f8d6c713ea6"
DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI:append = " file://0001-add-setup.py.patch"

SRC_URI[md5sum] = "82cb02f561ab5afe4f7c9e07574aef57"
SRC_URI[sha256sum] = "a58e94dd67871639abc091b57d32842449b230777570ef2bcec3dc16b912613e"

inherit pypi setuptools3


