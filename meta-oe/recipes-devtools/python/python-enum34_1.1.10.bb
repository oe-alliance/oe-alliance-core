SUMMARY = "backport of Python 3.4's enum package"
HOMEPAGE = "https://bitbucket.org/stoneleaf/enum34"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://enum/LICENSE;md5=0a97a53a514564c20efd7b2e8976c87e"

SRC_URI[md5sum] = "b5ac0bb5ea9e830029599e410d09d3b5"
SRC_URI[sha256sum] = "cce6a7477ed816bd2542d03d53db9f0db935dd013b70f336a95c73979289f248"

inherit pypi setuptools

BBCLASSEXTEND = "native nativesdk"
include python-package-split.inc
