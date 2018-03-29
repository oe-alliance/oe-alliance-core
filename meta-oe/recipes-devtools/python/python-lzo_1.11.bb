SUMMARY = "Python bindings for the LZO data compression library in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=dfeaf3dc4beef4f5a7bdbc35b197f39e"
SRCNAME = "lzo"

DEPENDS = "zlib lzo python-pip"

SRC_URI = "https://github.com/jd-boyd/python-lzo/archive/v${PV}.tar.gz"

SRC_URI[md5sum] = "4e758b3ff00a2e5c55020e37c431be61"
SRC_URI[sha256sum] = "b83104ce9786184545cd2e8d2a8c40a42abd8a7c61cf8970f9264e1972862b7a"

S = "${WORKDIR}/python-lzo-${PV}"

inherit distutils

include python-package-split.inc

