SUMMARY = "yEnc Module for Python modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=8b13430ed095d383d6be025495aa8257"

SRC_URI = "https://files.pythonhosted.org/packages/cf/56/d9ebe198a4c7c0e5f2e8f3ba973aed7a3d160c5499a857ced73657971441/sabyenc-${PV}.tar.gz"
SRC_URI[md5sum] = "f13c533fbae139731f1abe9775e77038"
SRC_URI[sha256sum] = "a4cacd0332577c52a4e416c18509ddf2bc9ea7f89637ac6d67b51a6142ac7ee1"

S = "${WORKDIR}/sabyenc-${PV}"

inherit setuptools

include python-package-split.inc
