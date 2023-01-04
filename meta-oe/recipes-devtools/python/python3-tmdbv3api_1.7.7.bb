SUMMARY = "A lightweight Python library for The Movie Database (TMDb) API."
HOMEPAGE = "https://github.com/AnthonyBloomer/tmdbv3api"
AUTHOR = "Anthony Bloomer <ant0@protonmail.ch>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://setup.py;md5=be13b89d31a44a681db106b918c80249"

SRC_URI = "https://files.pythonhosted.org/packages/25/b9/265b16667bc145d806d52478879ac6d3453b0b2bddce48803c36e74e76b8/tmdbv3api-1.7.7.tar.gz"
SRC_URI[md5sum] = "57e319c4e7348d602467258603fd4f74"
SRC_URI[sha256sum] = "95c8987e9e0d3b0b0263e3f3c1cb6e594eecf2627564830123652b18f7b64469"

S = "${WORKDIR}/tmdbv3api-1.7.7"

RDEPENDS:${PN} += "${PYTHON_PN}-requests"

inherit setuptools3

include ${PYTHON_PN}-package-split.inc
