SUMMARY = "Asynchronous Component based Event Application Framework"
HOMEPAGE = "http://circuitsframework.com/"
AUTHOR = "James Mills <prologic@shortcircuit.net.au>"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b209f6edbb40680bdf62b70a7c097101"

DEPENDS = "python3-setuptools-scm-native"

SRCREV = "4c9496fe79edaa4e1e2ae4f92776c29bab80117f"
SRC_URI = "git://github.com/circuits/circuits.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit setuptools3

include python3-package-split.inc
