SUMMARY = "A lightweight Python library for The Movie Database (TMDb) API."
HOMEPAGE = "https://github.com/AnthonyBloomer/tmdbv3api"
AUTHOR = "Anthony Bloomer <ant0@protonmail.ch>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://setup.py;md5=48a646f54ae463050b816658ad6d73e9"

SRC_URI[md5sum] = "8668bf95b9a40488b2c1a3771e543f50"
SRC_URI[sha256sum] = "504c5da6b99c4516ff160a01576112d097f209c0534f943c15c4b56cbd92c33b"

RDEPENDS:${PN} += "${PYTHON_PN}-requests"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
