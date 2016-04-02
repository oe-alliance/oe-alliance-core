DESCRIPTION = "minimal, cross platform, C++ GUID library"
SECTION = "devel"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README.md;md5=a7dbd11cb99ed74462135e2e5c074b44"

DEPENDS = "util-linux"

PV = "1.0"
PR = "r0"

SRC_URI = "http://ftp.halifax.rwth-aachen.de/xbmc/build-deps/sources/crossguid-8f399e8bd4.tar.gz"

SRC_URI[md5sum] = "7de3be575744da5f1098295485ef0741"
SRC_URI[sha256sum] = "3d77d09a5df0de510aeeb940df4cb534787ddff3bb1828779753f5dfa1229d10"

FILES_${PN} = "/"

S = "${WORKDIR}/crossguid-8f399e8bd4"


do_compile() {
    $CXX -c guid.cpp -o guid.o $CXXFLAGS -std=c++11 -DGUID_LIBUUID
    $AR rvs libcrossguid.a guid.o
}

do_install() {
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    install -m 0644 ${S}/guid.h ${D}/${includedir}/guid.h
    install -m 0755 ${S}/libcrossguid.a ${D}/${libdir}/
}
