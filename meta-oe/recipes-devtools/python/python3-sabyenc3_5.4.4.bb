SUMMARY = "yEnc Module for Python3 modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=f82454b2c2160caf008468993f9146b8"

SRC_URI:append = " file://0001-disable-platformcheck.patch"

SRC_URI[md5sum] = "60382280a30724558ef967cb6eaad37f"
SRC_URI[sha256sum] = "f3d65f2a70bcb13ef1beae0ff6bb3b69adae18497035f8cd4ffe4e5af1aa2f41"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
