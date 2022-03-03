SUMMARY = "NFS client library"

LICENSE = "LGPL-2.1-only & GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "4.0.0+git"
SRCREV = "030814506e529ef1f1572c7b6e3fc2e4c10cb544"

SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
