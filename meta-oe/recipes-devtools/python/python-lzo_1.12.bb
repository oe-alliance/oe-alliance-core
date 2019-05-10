SUMMARY = "Python bindings for the LZO data compression library in Python"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=dfeaf3dc4beef4f5a7bdbc35b197f39e"
SRCNAME = "lzo"

DEPENDS = "zlib lzo python-pip"

SRC_URI = "https://files.pythonhosted.org/packages/af/60/41f17f56c920a956f1d4b9f04f9755c045b2b06b9dd933b33cdd37ab9fd7/python-lzo-${PV}.tar.gz"

SRC_URI[md5sum] = "9cebb6e2be79d59def6a67b5f93b5dfd"
SRC_URI[sha256sum] = "97a8e46825e8f1abd84c2a3372bc09adae9745a5be5d3af2692cd850dac35345"

S = "${WORKDIR}/python-lzo-${PV}"

inherit distutils

include python-package-split.inc
