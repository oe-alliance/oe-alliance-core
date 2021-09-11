DESCRIPTION = "Python yEnc package optimized for use within SABnzbd"
SECTION = "devel/python"
DEPENDS = "python"
PRIORITY = "optional"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=8b13430ed095d383d6be025495aa8257"


inherit pypi setuptools

SRC_URI[md5sum] = "f13c533fbae139731f1abe9775e77038"
SRC_URI[sha256sum] = "a4cacd0332577c52a4e416c18509ddf2bc9ea7f89637ac6d67b51a6142ac7ee1"

include python-package-split.inc
