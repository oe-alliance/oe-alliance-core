SUMMARY = "sabyenc - yEnc Module for Python modified for SABnzbd"
HOMEPAGE = "https://github.com/sabnzbd/sabnzbd-yenc"
SECTION = "devel/python"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=e54dd3fed585b56af7b9dc51c5130c4b"

SRC_URI = "https://files.pythonhosted.org/packages/4f/30/a1a313ee4d9881cc5e119fcefb9f746df71f1b4fa6d3b53cc334799dbfd8/sabyenc-${PV}.tar.gz"

SRC_URI[md5sum] = "2d638759546a74635df85cc59e8633c6"
SRC_URI[sha256sum] = "0571ced68c4b7a8cc08733b9c2f4d137108f405dbe30b8805d33b8c96a69fb3a"

S = "${WORKDIR}/sabyenc-${PV}"

inherit setuptools

include python-package-split.inc
