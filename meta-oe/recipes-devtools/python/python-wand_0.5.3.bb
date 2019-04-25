DESCRIPTION = "Wand is a ctypes-based simple ImageMagick binding for Python"
HOMEPAGE = "http://docs.wand-py.org/en/0.5.3"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=637f1431f188c8bd11d207b553fa9090"

DEPENDS += " imagemagick-native"

SRC_URI = "git://github.com/dahlia/wand;protocol=https;tag=${PV}"

S = "${WORKDIR}/git"

inherit setuptools

BBCLASSEXTEND = "native"

include python-package-split.inc
