SUMMARY = "Python library for ISO 639 standard"
LICENSE = "AGPL-3.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=80737208b75a29a53186c67ddff06781;beginline=11;endline=14"

inherit pypi setuptools

PYPI_PACKAGE = "iso-639"

PACKAGES = "${PN}"

SRC_URI[md5sum] = "cc282daf57f57061a9309f2567bff052"
SRC_URI[sha256sum] = "dc9cd4b880b898d774c47fe9775167404af8a85dd889d58f9008035109acce49"

BBCLASSEXTEND = "native nativesdk"
