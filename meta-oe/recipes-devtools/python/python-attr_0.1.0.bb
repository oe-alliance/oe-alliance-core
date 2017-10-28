SUMMARY = "Simple decorator to set attributes of target function or class in a DRY way."
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=9e0380ea0f5b7c92687a86ca88562064"

PR = "r0"

SRC_URI = "https://pypi.python.org/packages/source/a/attr/attr-${PV}.tar.gz"

SRC_URI[md5sum] = "d59f397a3124da33118daaca4e45b2a8"
SRC_URI[sha256sum] = "5031f87ddca9f647dcc0ef38db37f2f2ba4f519c73f1b392f7a87ef47a2f298d"

S = "${WORKDIR}/attr-${PV}"

inherit setuptools

include python-package-split.inc
