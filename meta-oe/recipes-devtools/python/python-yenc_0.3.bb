SUMMARY = "yEnc module for Python"
SECTION = "devel/python"
DEPENDS = "python"
PRIORITY = "optional"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING;md5=e673a95e6911049cc1cadf00eac1f759"

SRCNAME = "yenc"

SRC_URI = "http://www.golug.it/pub/yenc/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils

SRC_URI[md5sum] = "5bdcad71607e4bf7874c9d5fa203c7c7"
SRC_URI[sha256sum] = "88b3ea30c93c24a162874eeb3c1920ac7254953342578a5d63d075963478437e"

include python-package-split.inc
