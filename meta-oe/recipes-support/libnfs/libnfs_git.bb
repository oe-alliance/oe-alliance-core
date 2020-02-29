SUMMARY = "NFS client library"

LICENSE = "LGPLv2.1 & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=825301ba17efc9d188ee0abd4b924ada"

PV = "2.0.0+git"
SRCREV = "6a33413b0f684327e1441f5ec5c4493293009d53"
SRC_URI = "git://github.com/sahlberg/libnfs.git;protocol=https \
           file://0001-include-sys-time.h-to-fix-musl-build.patch \
           file://0001-CMakeLists.txt-fix-library-install-path.patch \
          "

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DBASE_LIB_PATH=${baselib}"
