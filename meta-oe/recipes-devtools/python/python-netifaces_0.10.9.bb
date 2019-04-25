SUMMARY = "Portable network interface information."
HOMEPAGE = "https://pypi.org/project/netifaces"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit setuptools pypi

SRC_URI[md5sum] = "de92cc322b4f56047c073f802ad77860"
SRC_URI[sha256sum] = "2dee9ffdd16292878336a58d04a20f0ffe95555465fee7c9bd23b3490ef2abf3"

include python-package-split.inc
