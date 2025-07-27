SUMMARY = "Pure python implementation of magic file detection"
HOMEPAGE = "https://github.com/cdgriffith/puremagic"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ac85ec1f210835a5806bc00df0be30d7"

SRC_URI[md5sum] = "6dffa5de27c361c21492c74a7d97a119"
SRC_URI[sha256sum] = "f9ff7ac157d54e9cf3bff1addfd97233548e75e685282d84ae11e7ffee1614c9"

inherit pypi setuptools3

include python3-package-split.inc
