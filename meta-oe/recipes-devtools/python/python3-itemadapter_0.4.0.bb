SUMMARY  = "The ItemAdapter class is a wrapper for data container objects, providing a common interface to handle objects of different types in an uniform manner, regardless of their underlying implementation."
HOMEPAGE = "https://github.com/scrapy/itemadapter"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a669d9fc44456c80ce6829e1b809742c"

SRC_URI[md5sum] = "0d5d5cc8fba8821bd43ee18174094977"
SRC_URI[sha256sum] = "f05df8da52619da4b8c7f155d8a15af19083c0c7ad941d8c1de799560ad994ca"

S = "${WORKDIR}/itemadapter-${PV}"

inherit pypi setuptools3

include ${PYTHON_PN}-package-split.inc
