SUMMARY = "Portable network interface information."
HOMEPAGE = "https://pypi.python.org/pypi/netifaces"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "https://pypi.python.org/packages/a7/4c/8e0771a59fd6e55aac993a7cc1b6a0db993f299514c464ae6a1ecf83b31d/netifaces-0.10.5.tar.gz"

PV = "0.10.5"
PR = "r1"

S = "${WORKDIR}/netifaces-${PV}"

inherit setuptools

include python-package-split.inc

SRC_URI[md5sum] = "5b4d1f1310ed279e6df27ef3a9b71519"
SRC_URI[sha256sum] = "59d8ad52dd3116fcb6635e175751b250dc783fb011adba539558bd764e5d628b"
