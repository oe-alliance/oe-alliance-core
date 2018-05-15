DESCRIPTION = "Lightweight cross platform C++ GUID/UUID library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1373274bc8d8001edc54933919f36f68"

DEPENDS = "util-linux"

PR = "r0"
inherit autotools pkgconfig

SRC_URI = "\
	git://github.com/graeme-hill/crossguid.git;protocol=http \
	file://crossguid.pc \
"
SRCREV = "8f399e8bd4252be9952f3dfa8199924cc8487ca4"

S = "${WORKDIR}/git"

EXTRA_CXXFLAGS = "-I. -fPIC -Wall -std=c++11 -DGUID_LIBUUID"

do_compile() {
    cd ${S}
    ${CXX} ${CXXFLAGS} ${EXTRA_CXXFLAGS} -c -o guid.o guid.cpp
    ${AR} rvs libcrossguid.a guid.o
}

do_install() {
	mkdir -p ${D}/usr/include
	mkdir -p ${D}${libdir}/pkgconfig
	install -m 644 ${S}/guid.h ${D}/usr/include
	install -m 644 ${S}/libcrossguid.a ${D}${libdir}
	install -m 644 ${WORKDIR}/crossguid.pc ${D}${libdir}/pkgconfig
}

do_package_qa() {
}

FILES_${PN} = "/"

