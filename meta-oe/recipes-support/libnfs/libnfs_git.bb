SUMMARY = "NFS client library"

LICENSE = "LGPLv2.1 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "4.0.0+git"
SRCREV = "030814506e529ef1f1572c7b6e3fc2e4c10cb544"

SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
