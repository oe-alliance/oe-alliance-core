SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

SRC_URI[md5sum] = "f19f82585fff73f279ac1e84d5dff73a"
SRC_URI[sha256sum] = "77758485fb0ac10730d4b131363e37d65cb8db2450bfec7a57c3f3271f4a48a9"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
