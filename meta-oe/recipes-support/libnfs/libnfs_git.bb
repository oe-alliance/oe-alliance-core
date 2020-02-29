SUMMARY = "NFS client library"

LICENSE = "LGPLv2.1 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "3.0.0+git"
SRCREV = "695ac3b77b7632406a3946886e42a2a69dc5b21b"
SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https \
          "

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
